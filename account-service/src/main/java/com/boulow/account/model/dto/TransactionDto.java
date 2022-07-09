package com.boulow.account.model.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.boulow.account.model.TransactionStatus;
import com.boulow.account.model.TransactionType;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransactionDto {

	private Long id;
	private BigDecimal amount;
	private BigDecimal availableBalance;
	private TransactionType type;
	private TransactionStatus state;
	private String originatorName;
	private String beneficiaryName;
	private String currency;
	private String notes;
	private String rejectionReason;
	private String beneAccNum;
	private String origAccNum;
	private Date transactionDateTime;
	private Long paymentId;
	private Long accountId;
	private PaymentDto payment;
}
