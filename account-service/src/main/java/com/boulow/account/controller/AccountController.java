package com.boulow.account.controller;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.boulow.account.mapper.AccountMapper;
import com.boulow.account.model.dto.AccountDto;
import com.boulow.account.model.dto.TransactionDto;
import com.boulow.account.service.AccountService;

@RestController
@RequestMapping(value = "v1/acct")
public class AccountController {

	@Autowired
	private AccountService accountService;

	@Autowired
	private AccountMapper accountMapper;

	static final Logger logger = LoggerFactory.getLogger(AccountController.class);

	@GetMapping(value = "/")
	public ResponseEntity<List<AccountDto>> getAllAccounts(
			@RequestHeader(value = "Accept-Language", required = false) Locale locale) {
		return ResponseEntity.ok(accountService.findAll(locale).stream()
				.map(account -> accountMapper.convertToDto(account)).collect(Collectors.toList()));
	}
	
	@PostMapping(value = "/transact")
	public ResponseEntity<TransactionDto> transact(@RequestHeader(value = "Accept-Language", required = false) Locale locale, @RequestBody TransactionDto transactionDto) {
		logger.info(transactionDto.toString());
		switch (transactionDto.getType()) {
		case DEPOSIT:
			transactionDto = accountService.deposit(transactionDto, locale);
			break;
		case WITHDRAWAL:
			transactionDto = accountService.withdraw(transactionDto, locale);
			break;	
		case TRANSFER:
			transactionDto = accountService.transfer(transactionDto, locale);
			break;
		case REIMBURSMENT:
			
			break;
		default:
			break;
		}
		return ResponseEntity.ok(transactionDto);
	}
	
}
