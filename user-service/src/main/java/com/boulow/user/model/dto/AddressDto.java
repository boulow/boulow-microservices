package com.boulow.user.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.boulow.user.model.AddressType;
import com.boulow.user.model.AppUser;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {

    private String street;
    private String suite;
    private String city;
    private String province;
    private String zip;
    private AddressType type;
    private String country;
    private Long id;
    private AppUser user;

}
