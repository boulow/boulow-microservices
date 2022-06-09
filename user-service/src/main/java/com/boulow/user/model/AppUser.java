package com.boulow.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Nationalized;

/**
 * A user.
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AppUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty
    private Long id;

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
    
    private boolean isEmailVerified = false;
	
    @Size(max = 50)
    private String issuer;
    
    private String uid;

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
    
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<AppRole> roles;
    
    public void addAddress(Address address) {
    	addresses.add(address);
    	address.setUser(this);
    }
 
    public void removeAddress(Address address) {
    	addresses.remove(address);
    	address.setUser(null);
    }

}
