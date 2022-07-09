package com.boulow.account.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@Entity
@DiscriminatorValue("INV")
public class InvestmentAccount extends Account {

	public InvestmentAccount() {
		super("INV-");
	}
	
	private Long tribeId;
	
	private Long investmentId;
}
