package com.boulow.account.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@Entity
@DiscriminatorValue("LOA")
public class LoanAccount extends Account {

	public LoanAccount() {
		super("LOA-");
		this.isDefault = false;
	}
	
	@JsonProperty
	private float interestRate;
	
	@JsonProperty
	private BigDecimal principal;
	
	@JsonProperty
	private BigDecimal minimumDue;
	
	@JsonProperty
	private BigDecimal feesPaid;
	
	@JsonProperty
	private Date lastPaymentDate;
	
	@JsonProperty
	private Date nextPaymentDate;
	
	@JsonProperty
	private Date firstPaymentDate;
	
	@JsonProperty
	private int installments;
	
	@JsonProperty
	private boolean isDefault;
	
	@JsonProperty
	private String purpose;

}
