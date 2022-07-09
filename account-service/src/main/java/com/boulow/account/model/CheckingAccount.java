package com.boulow.account.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@Entity
@DiscriminatorValue("CHK")
public class CheckingAccount extends Account {

	public CheckingAccount() {
		super("CHK-");
	}
	
}
