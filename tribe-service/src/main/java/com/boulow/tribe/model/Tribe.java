package com.boulow.tribe.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Nationalized;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@Entity
public class Tribe {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty
	private long id;
	
	@JsonProperty
	@Column(unique = true, length = 45)
	@Nationalized
	private String name;

	@JsonProperty
	@Column(length = 1500)
	@Nationalized
	private String shortDesc;

	@JsonProperty
	private String logoUrl;
	
	@JsonProperty
	@Embedded
	private Address address;
	
	@JsonProperty
	private int membersCnt;
	
	@OneToMany(
	        mappedBy = "tribe",
	        cascade = CascadeType.ALL,
	        orphanRemoval = true,
	        fetch = FetchType.LAZY
	    )
	private List<Member> members = new ArrayList<>();
	
	@OneToMany(
	        mappedBy = "tribe",
	        cascade = CascadeType.ALL,
	        orphanRemoval = true,
	        fetch = FetchType.LAZY
	    )
	private List<Investment> investments = new ArrayList<>();
	
	@OneToMany(mappedBy = "tribe",
	        cascade = CascadeType.ALL,
	        orphanRemoval = true,
	        fetch = FetchType.LAZY)
	private Set<TribeParameter> params;
	
	@JsonProperty
	@Enumerated(EnumType.ORDINAL)
    private Visibility visibility;
	
	@JsonProperty
	@Column(precision=12, scale=4)
	private BigDecimal minContribution;
	
	@JsonProperty
	@Column(length = 30)
	private String acctNum;
	
	@JsonProperty
	@Column(length = 20)
	private String phoneNumber;
	
	@JsonProperty
	@Column(length = 15)
	private String routing;
	
	@JsonProperty
	@Column(length = 50)
	private String fiName;
	
	public void addMember(Member member) {
		members.add(member);
		member.setTribe(this);
		this.membersCnt++;
    }
 
    public void removeMember(Member member) {
    	members.remove(member);
    	member.setTribe(null);
    	this.membersCnt--;
    }
    
    public void addInvestment(Investment investment) {
    	investments.add(investment);
		investment.setTribe(this);
    }
 
    public void removeInvestment(Investment investment) {
    	investments.remove(investment);
    	investment.setTribe(null);
    }
    
    public void addParams(TribeParameter... parameters) {
    	for(TribeParameter param: parameters)
    		param.setTribe(this);
    	this.params = Stream.of(parameters).collect(Collectors.toSet());
    }

}
