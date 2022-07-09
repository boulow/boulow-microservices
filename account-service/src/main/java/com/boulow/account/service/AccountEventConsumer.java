package com.boulow.account.service;

import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.boulow.account.event.AccountEvent;

import lombok.AllArgsConstructor;

@Component("accountEventConsumer")
@AllArgsConstructor
public class AccountEventConsumer implements Consumer<AccountEvent> {
	
	static final Logger logger = LoggerFactory.getLogger(AccountEventConsumer.class);
	
	@Autowired
	private AccountService accountService;
	
	@Override
	public void accept(AccountEvent event) {
		logger.info("Creating account event for correlation Id: " + event.getCorrelationId());
		switch (event.getType()) {
		case CREATE:
			createAccount(event);
			break;
		case DELETE:
			accountService.delete(event.getAccountId());
			break;
		default:
			break;
		}	
	}
	
	public void createAccount(AccountEvent event) {
		switch (event.getAcctType()) {
		case LOA:
			accountService.createLoanAccount(event.getMembershipId());;
			break;
		case INV:
			accountService.createInvestmentAccount(event.getInvestmentId(), event.getTribeId());
			break;
		default:
			break;
		}
	}

}
