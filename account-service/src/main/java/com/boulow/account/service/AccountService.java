package com.boulow.account.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.boulow.account.event.AccountType;
import com.boulow.account.exception.ResourceNotFoundException;
import com.boulow.account.mapper.TransactionMapper;
import com.boulow.account.model.Account;
import com.boulow.account.model.CheckingAccount;
import com.boulow.account.model.InternalAccount;
import com.boulow.account.model.InvestmentAccount;
import com.boulow.account.model.LoanAccount;
import com.boulow.account.model.SavingsAccount;
import com.boulow.account.model.Transaction;
import com.boulow.account.model.TransactionStatus;
import com.boulow.account.model.dto.PaymentDto;
import com.boulow.account.model.dto.TransactionDto;
import com.boulow.account.model.dto.TribeDto;
import com.boulow.account.repository.AccountRepository;
import com.boulow.account.security.utils.UserContext;
import com.boulow.account.security.utils.UserContextHolder;
import com.boulow.account.service.client.PaymentFeignClient;
import com.boulow.account.service.client.TribeFeignClient;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.bulkhead.annotation.Bulkhead.Type;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

@Service("accountService")
@Transactional
public class AccountService {

	static final Logger logger = LoggerFactory.getLogger(AccountService.class);

	@Autowired
	MessageSource messages;

	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private TransactionMapper transactionMapper;

	@Autowired
	private TribeFeignClient tribeFeignClient;
	
	@Autowired
	private PaymentFeignClient paymentFeignClient;

	public List<Account> findByMembership(Long membershipId) {
		return accountRepository.findByMembershipId(membershipId);
	}

	public Iterable<Account> createUserDefaultAccounts(Long membershipId) {
		if (checkMembershipId(membershipId))
			return createChkAndSavAccounts(membershipId);
		else
			throw new ResourceNotFoundException("membership.invalid.argument");
	}

	public List<Account> findAll(Locale locale) {
		List<Account> accounts = new ArrayList<>();
		accountRepository.findAll().forEach(accounts::add);
		return accounts;
	}

	public void delete(Long accountId) {
		Account acct = accountRepository.findById(accountId)
				.orElseThrow(() -> new ResourceNotFoundException("Account not found"));
		accountRepository.delete(acct);
	}
	
	public Account findByAccountNum(String acctNum) {
		return accountRepository.findByAccountNumber(acctNum)
				.orElseThrow(() -> new ResourceNotFoundException("Account not found"));
	}

	public void createAcctNewTribe(Long membershipId, Long tribeId) {
		createInternalAccount(membershipId, tribeId);
		createUserDefaultAccounts(membershipId);
	}

	public Iterable<Account> createChkAndSavAccounts(Long membershipId) {
		List<Account> myAccounts = new ArrayList<>();
		myAccounts.add(new CheckingAccount());
		myAccounts.add(new SavingsAccount());
		myAccounts.forEach(acct -> acct.setMembershipId(membershipId));
		return accountRepository.saveAll(myAccounts);
	}

	public void createInternalAccount(Long membershipId, Long tribeId) {
		InternalAccount internalAcct = new InternalAccount();
		internalAcct.setMembershipId(membershipId);
		internalAcct.setTribeId(tribeId);
		accountRepository.save(internalAcct);
	}

	public void createInvestmentAccount(Long investmentId, Long tribeId) {
		InvestmentAccount investmentAcct = new InvestmentAccount();
		investmentAcct.setInvestmentId(investmentId);
		investmentAcct.setTribeId(tribeId);
		accountRepository.save(investmentAcct);
	}

	public void createLoanAccount(Long membershipId) {
		LoanAccount loanAcct = new LoanAccount();
		loanAcct.setMembershipId(membershipId);
		accountRepository.save(loanAcct);
	}

	public boolean hasEnoughFunds(Account acct, BigDecimal amount) {
		BigDecimal remainingBal = acct.getAvailableBalance().add(amount.negate());
		boolean hasEnoughFunds = true;
		if (remainingBal.signum() < 0) {
			hasEnoughFunds = false;
		}
		return hasEnoughFunds;
	}

	public TransactionDto deposit(TransactionDto transactionDto, Locale locale) {
		Account acct = accountRepository.findById(transactionDto.getAccountId())
				.orElseThrow(() -> new ResourceNotFoundException("Account not found"));
		BigDecimal transAmount = transactionDto.getAmount();
		Date transDate = new Date();
		
		// Credit tribe's account
		TribeDto tribe = getTribeByMember(acct.getMembershipId());
		Account tribeAcct = accountRepository.findByTribeIdAndAcctType(tribe.getId(), AccountType.INT.name());
		tribeAcct = credit(tribeAcct, transAmount);
		
		// Initiate Payment from Tribe's account to member's external account
		//PaymentDto payment = initiatePayment(locale, transactionDto.getPayment()).getBody();
		
		// Credit Transaction main ledger
		Transaction ledgerTrans = transactionMapper.convertFromDto(transactionDto);
		ledgerTrans.setState(TransactionStatus.PENDING);
		ledgerTrans.setTransactionDateTime(transDate);
		ledgerTrans.setOrigAccNum(acct.getAccountNumber());
		ledgerTrans.setAvailableBalance(tribeAcct.getAvailableBalance());
		ledgerTrans.setBeneAccNum(tribeAcct.getAccountNumber());
		//transaction.setPaymentId(payment.getId());
		tribeAcct.addTransaction(ledgerTrans);
		
		// Credit member's account
		Transaction mbrTrans = transactionMapper.convertFromDto(transactionDto);
		mbrTrans.setState(TransactionStatus.PENDING);
		mbrTrans.setTransactionDateTime(transDate);
		mbrTrans.setBeneAccNum(acct.getAccountNumber());
		acct = credit(acct, transAmount);
		mbrTrans.setAvailableBalance(acct.getAvailableBalance());
		acct.addTransaction(mbrTrans);
		
		accountRepository.saveAll(Arrays.asList(acct, tribeAcct));
		return transactionDto;
	}

	public TransactionDto withdraw(TransactionDto transactionDto, Locale locale) {
		Account acct = accountRepository.findById(transactionDto.getAccountId())
				.orElseThrow(() -> new ResourceNotFoundException("Account not found"));
		BigDecimal transAmount = transactionDto.getAmount();
		// Check balance
		if (hasEnoughFunds(acct, transAmount)) {
			Date transDate = new Date();
			// Debit tribe's account
			TribeDto tribe = getTribeByMember(acct.getMembershipId());
			Account tribeAcct = accountRepository.findByTribeIdAndAcctType(tribe.getId(), AccountType.INT.name());
			tribeAcct = debit(tribeAcct, transAmount);
			
			// Initiate Payment from Tribe's account to member's external account
			//PaymentDto payment = initiatePayment(locale, transactionDto.getPayment()).getBody();
			
			// Debit Transaction main ledger
			Transaction ledgerTrans = transactionMapper.convertFromDto(transactionDto);
			ledgerTrans.setState(TransactionStatus.PENDING);
			ledgerTrans.setTransactionDateTime(transDate);
			ledgerTrans.setOrigAccNum(tribeAcct.getAccountNumber());
			ledgerTrans.setBeneAccNum(acct.getAccountNumber());
			ledgerTrans.setAvailableBalance(tribeAcct.getAvailableBalance());
			//transaction.setPaymentId(payment.getId());
			tribeAcct.addTransaction(ledgerTrans);
			
			// Debit member's account
			Transaction mbrTrans = transactionMapper.convertFromDto(transactionDto);
			mbrTrans.setState(TransactionStatus.PENDING);
			mbrTrans.setTransactionDateTime(transDate);
			mbrTrans.setOrigAccNum(acct.getAccountNumber());
			acct = debit(acct, transAmount);
			mbrTrans.setAvailableBalance(acct.getAvailableBalance());
			acct.addTransaction(mbrTrans);
			
			accountRepository.saveAll(Arrays.asList(acct, tribeAcct));
			
		} else {
			transactionDto.setRejectionReason("Insufficient Funds.");
		}
		return transactionDto;
	}

	public TransactionDto transfer(TransactionDto transactionDto, Locale locale) {
		Account bene = findByAccountNum(transactionDto.getBeneAccNum());
		Account orig = findByAccountNum(transactionDto.getOrigAccNum());
		BigDecimal transAmount = transactionDto.getAmount();
		if (hasEnoughFunds(orig, transAmount)) {
			Date transDate = new Date();
			
			// Debit originator's account
			orig = debit(orig, transAmount);
			Transaction debitTrans = transactionMapper.convertFromDto(transactionDto);
			debitTrans.setState(TransactionStatus.CLEARED);
			debitTrans.setTransactionDateTime(transDate);
			debitTrans.setBeneAccNum(bene.getAccountNumber());
			debitTrans.setAvailableBalance(orig.getAvailableBalance());
			orig.addTransaction(debitTrans);
			
			// Credit originator's account
			bene = credit(bene, transAmount);
			Transaction creditTrans = transactionMapper.convertFromDto(transactionDto);
			creditTrans.setState(TransactionStatus.CLEARED);
			creditTrans.setTransactionDateTime(transDate);
			creditTrans.setOrigAccNum(orig.getAccountNumber());
			creditTrans.setAvailableBalance(bene.getAvailableBalance());
			bene.addTransaction(creditTrans);
			
			// Update Ledger
			TribeDto tribe = getTribeByMember(orig.getMembershipId());
			Account tribeAcct = accountRepository.findByTribeIdAndAcctType(tribe.getId(), AccountType.INT.name());
			Transaction ledgerTrans = transactionMapper.convertFromDto(transactionDto);
			ledgerTrans.setState(TransactionStatus.CLEARED);
			ledgerTrans.setTransactionDateTime(transDate);
			ledgerTrans.setOrigAccNum(orig.getAccountNumber());
			ledgerTrans.setBeneAccNum(bene.getAccountNumber());
			ledgerTrans.setAvailableBalance(tribeAcct.getCurrentBalance());
			tribeAcct.addTransaction(ledgerTrans);
			
			// Save transactions
			accountRepository.saveAll(Arrays.asList(orig, bene, tribeAcct));
		} else {
			transactionDto.setRejectionReason("Insufficient Funds.");
		}
		return transactionDto;
	}

	public Account credit(Account acct, BigDecimal amount) {
		acct.setAvailableBalance(acct.getAvailableBalance().add(amount));
		return acct;
	}

	public Account debit(Account acct, BigDecimal amount) {
		acct.setAvailableBalance(acct.getAvailableBalance().add(amount.negate()));
		return acct;
	}

	@CircuitBreaker(name = "tribeService")
	@Bulkhead(name = "bulkheadTribeService", type = Type.THREADPOOL)
	@Retry(name = "retryTribeService")
	public boolean checkTribeId(Long tribeId) {
		UserContextHolder.getContext();
		return tribeFeignClient.validateTribeId(tribeId, UserContext.getAuthToken());
	}
	
	public ResponseEntity<PaymentDto> initiatePayment(Locale locale, PaymentDto payment) {
		UserContextHolder.getContext();
		return paymentFeignClient.initiatePayment(locale, payment, UserContext.getAuthToken());
	}

	@CircuitBreaker(name = "tribeService")
	@Bulkhead(name = "bulkheadTribeService", type = Type.THREADPOOL)
	@Retry(name = "retryTribeService")
	public TribeDto getTribeByMember(Long memberId) {
		UserContextHolder.getContext();
		return tribeFeignClient.getTribeByMemberId(memberId, UserContext.getAuthToken());
	}

	@CircuitBreaker(name = "tribeService")
	@Bulkhead(name = "bulkheadTribeService", type = Type.THREADPOOL)
	@Retry(name = "retryTribeService")
	public boolean checkMembershipId(Long membershipId) {
		UserContextHolder.getContext();
		return tribeFeignClient.validateMembershipId(membershipId, UserContext.getAuthToken());
	}
}
