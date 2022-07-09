package com.boulow.account.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("transactionService")
@Transactional
public class TransactionService {
	
	static final Logger logger = LoggerFactory.getLogger(TransactionService.class);

	@Autowired
	MessageSource messages;
	
	public void updateTransactionStatus() {}
}
