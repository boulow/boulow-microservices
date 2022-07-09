package com.boulow.payment.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A user.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AppUser {

    @JsonProperty
    private String firstName;

    @JsonProperty
    private String lastName;

    @JsonProperty
    private String email;

    @JsonProperty
    private String langKey;

    @JsonProperty
    private String avatarUrl;
	
    private String issuer;
    
    private String uid;

    @JsonProperty
    private String phone;
    
    private boolean isEmailVerified = false;

}
