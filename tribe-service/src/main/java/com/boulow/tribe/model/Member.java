package com.boulow.tribe.model;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@Entity
public class Member {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty
	private long id;

	@ManyToOne
    private Tribe tribe;
	
	@JsonProperty
    private long userId;
    
    @JsonProperty
	@Enumerated(EnumType.ORDINAL)
    private MembershipStatus status = MembershipStatus.ACTIVE;
	
	@JsonProperty
	@Enumerated(EnumType.ORDINAL)
    private MembershipType type = MembershipType.BRONZE;
	
	@JsonProperty
	@Enumerated(EnumType.STRING)
	private MemberRole role = MemberRole.DEFAULT;
	
	@JsonProperty
	@Temporal(TemporalType.TIMESTAMP)
	private Date joinedOn = new Date();

}
