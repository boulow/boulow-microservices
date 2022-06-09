package com.boulow.account.model;

import java.math.BigDecimal;
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
public class Payment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty
	private long id;
	
	@ManyToOne
	private Account account;
	
	@JsonProperty
	private BigDecimal availableBal = new BigDecimal("0");
	
	@JsonProperty
	@Temporal(TemporalType.TIMESTAMP)
	private Date paymentDate;
	
	@JsonProperty
	@Enumerated(EnumType.ORDINAL)
    private PaymentStatus status;
	
	@JsonProperty
	@Enumerated(EnumType.STRING)
	private PaymentChannel channel;
	
	@JsonProperty
	@Enumerated(EnumType.STRING)
	private PaymentType type;
	
	@JsonProperty
	private String originator;
	
	@JsonProperty
	private String beneficiary;
	
	@JsonProperty
	private String origFiName;
	
	@JsonProperty
	private String origFiAccNum;
	
	@JsonProperty
	private String beneFiName;
	
	@JsonProperty
	private String beneFiAccNum;
}
