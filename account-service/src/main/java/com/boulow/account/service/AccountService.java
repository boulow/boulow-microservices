package com.boulow.account.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.boulow.account.exception.ResourceNotFoundException;
import com.boulow.account.model.Account;
import com.boulow.account.model.CheckingAccount;
import com.boulow.account.model.InternalAccount;
import com.boulow.account.model.LoanAccount;
import com.boulow.account.model.SavingsAccount;
import com.boulow.account.repository.AccountRepository;
import com.boulow.account.security.utils.UserContext;
import com.boulow.account.security.utils.UserContextHolder;
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
	private TribeFeignClient tribeFeignClient;
	
	public List<Account> findByMembership(Long membershipId) {
		return accountRepository.findByMembershipId(membershipId);
	}
	
	public Iterable<Account> createUserDefaultAccounts(Long membershipId) {
		if(checkMembershipId(membershipId))
			return createChkAndSavAccounts(membershipId);
		else
			throw new ResourceNotFoundException("membership.invalid.argument");
	}
	
	public List<Account> findAll(Locale locale) {
		List<Account> accounts = new ArrayList<>();
		accountRepository.findAll().forEach(accounts::add);
		return accounts;
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
	
	public void createLoanAccount(Long membershipId) {
		LoanAccount loanAcct = new LoanAccount();
		loanAcct.setMembershipId(membershipId);
		accountRepository.save(loanAcct);
	}
	
	@CircuitBreaker(name = "tribeService")
	@Bulkhead(name = "bulkheadTribeService", type = Type.THREADPOOL)
	@Retry(name = "retryTribeService")
	public boolean checkTribeId(Long tribeId) {
		UserContextHolder.getContext();
		return tribeFeignClient.validateTribeId(tribeId, UserContext.getAuthToken());
	}
	
	@CircuitBreaker(name = "tribeService")
	@Bulkhead(name = "bulkheadTribeService", type = Type.THREADPOOL)
	@Retry(name = "retryTribeService")
	public boolean checkMembershipId(Long membershipId) {
		UserContextHolder.getContext();
		return tribeFeignClient.validateMembershipId(membershipId, UserContext.getAuthToken());
	}
}
