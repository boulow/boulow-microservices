package com.boulow.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A Address.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty
    private Long id;

    @JsonProperty
    private String street;

    @JsonProperty
    private String suite;
    
    @ManyToOne
    private AppUser user;

    @JsonProperty
    private String city;

    @JsonProperty
    private String province;

    @JsonProperty
    private String zip;
    
    @JsonProperty
    private String country;

    @JsonProperty
    @Enumerated(EnumType.ORDINAL)
    private AddressType type;

}
