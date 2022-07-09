package com.boulow.tribe.model;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Nationalized;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@Entity
public class Investment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty
	private long id;
	
	@ManyToOne
    private Tribe tribe;
	
	@JsonProperty
	@Column(unique = true, length = 45)
	@Nationalized
	private String name;

	@JsonProperty
	@Column(length = 1500)
	@Nationalized
	private String shortDesc;

	@JsonProperty
	@Enumerated(EnumType.STRING)
	private InvestmentStatus state;
	
	@JsonProperty
	@Enumerated(EnumType.STRING)
	private InvestmentType type;
	
	@JsonProperty
	@Temporal(TemporalType.TIMESTAMP)
	private Date maturityDate = new Date();
	
	@JsonProperty
	private int term;
	
	@JsonProperty
	private BigDecimal capital;
	
	@JsonProperty
	private BigDecimal totalGains;
	
	@JsonProperty
	private BigDecimal totalDividends;
	
	@JsonProperty
	private double rateOfReturn;
}
