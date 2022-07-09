package com.boulow.account.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@Entity
@DiscriminatorValue("INT")
public class InternalAccount extends Account {

	public InternalAccount() {
		super("INT-");
	}
	
}
