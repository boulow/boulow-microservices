package com.boulow.account.model;

import java.math.BigDecimal;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@Entity
@DiscriminatorValue("INT")
public class InternalAccount extends Account {

	public InternalAccount() {
		super("INT-");
		this.currentBalance = new BigDecimal("0");
	}
	
	@JsonProperty
	private BigDecimal currentBalance;
	
	private Long tribeId;
	
}
