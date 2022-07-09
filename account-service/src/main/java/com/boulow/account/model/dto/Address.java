package com.boulow.account.model.dto;

import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Address {


	private String street;
    private String suite;
	private String city;
	private String state;
	private String country;
	private String zip;
}
