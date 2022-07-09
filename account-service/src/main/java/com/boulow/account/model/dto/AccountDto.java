package com.boulow.account.model.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.boulow.account.model.AccountState;
import com.boulow.account.model.Transaction;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountDto {

	private long id;
	private long membershipId;
	private BigDecimal availableBalance;
	private String accountNumber;
	private String acctType;
	private Date closeDate;
	private Date nextStmt;
    private AccountState state;
    @JsonIgnore
	private List<Transaction> transactions;
	private BigDecimal currentBalance;
	private float interestRate;
	private BigDecimal interestBalance;
	private Date lastCycleDate;
	private BigDecimal principal;
	private BigDecimal minimumDue;
	private BigDecimal feesPaid;
	private Date lastPaymentDate;
	private Date nextPaymentDate;
	private Date firstPaymentDate;
	private int installments;
	private boolean isDefault;
	private String purpose;
}
