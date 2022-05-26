package com.boulow.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Nationalized;

/**
 * A user.
 */
@Getter @Setter @ToString
@Entity
public class AppUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty
    private Long id;

    @NotNull
    @Size(min = 1, max = 50)
    @JsonProperty
    private String username;

    @Size(max = 50)
    @JsonProperty
    @Nationalized
    private String firstName;

    @Size(max = 50)
    @JsonProperty
    @Nationalized
    private String lastName;

    @Email
    @Size(min = 5, max = 254)
    @JsonProperty
    private String email;

    @NotNull
    @JsonProperty
    private boolean isActive = false;

    @Size(min = 2, max = 10)
    @JsonProperty
    private String langKey;

    @Size(max = 256)
    @JsonProperty
    private String avatarUrl;

    @JsonIgnore
    @Transient
    private Set<Authority> authorities = new HashSet<>();

    @Size(max = 300)
    @JsonProperty
    private String bio;

    @JsonProperty
    @Enumerated(EnumType.ORDINAL)
    private Gender gender;

    @Size(max = 20)
    @JsonProperty
    private String phone;

    @JsonProperty
    private LocalDate dob;

    @OneToMany(
	        mappedBy = "user",
	        cascade = CascadeType.ALL,
	        orphanRemoval = true,
	        fetch = FetchType.LAZY
	    )
    private List<Address> addresses = new ArrayList<>();

}
