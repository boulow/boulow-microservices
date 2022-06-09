package com.boulow.account.service;

import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.boulow.account.event.TribeEvent;

import lombok.AllArgsConstructor;

@Component("newTribeEventConsumer")
@AllArgsConstructor
public class NewTribeEventConsumer implements Consumer<TribeEvent> {
	
	static final Logger logger = LoggerFactory.getLogger(NewTribeEventConsumer.class);
	
	@Autowired
	private AccountService accountService;
	
	@Override
	public void accept(TribeEvent event) {
		logger.info("Creating Accounts for tribe " + event.getTribeId());
		accountService.createAcctNewTribe(event.getMembershipId(), event.getTribeId());		
	}

}
