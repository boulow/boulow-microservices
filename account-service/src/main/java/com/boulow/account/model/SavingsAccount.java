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
@DiscriminatorValue("SAV")
public class SavingsAccount extends Account {
	public SavingsAccount() {
		super("SAV-");
		this.currentBalance = new BigDecimal("0");
	}

	@JsonProperty
	private BigDecimal currentBalance;
	
	@JsonProperty
	private float interestRate;
	
	@JsonProperty
	private BigDecimal interestBalance;
	
	@JsonProperty
	private Date lastCycleDate;
}
