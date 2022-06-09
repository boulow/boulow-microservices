package com.boulow.account.model.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.boulow.account.model.Account;
import com.boulow.account.model.TransactionType;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransactionDto {

	private long id;
	private BigDecimal amount;
	private TransactionType type;
	private String originatorName;
	private String notes;
	private String originatorAccNum;
	private Date transactionDateTime;
	private Long paymentId;
	private Account account;
}
