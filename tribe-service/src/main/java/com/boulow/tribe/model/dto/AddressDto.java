package com.boulow.tribe.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {

    private String street;
    private String suite;
    private String city;
    private String state;
    private String zip;
	private String country;

}
