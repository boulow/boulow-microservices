package com.boulow.account.model;

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

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@Entity
public class Transaction {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty
	private long id;
	
	@JsonProperty
	private BigDecimal amount;
	
	@JsonProperty
	@Enumerated(EnumType.ORDINAL)
	private TransactionType type;
	
	@JsonProperty
	private String originatorName;
	
	@JsonProperty
	@Column(name = "notes", length=1000)
	private String notes;
	
	@JsonProperty
	private String originatorAccNum;
	
	@JsonProperty
	private Date transactionDateTime;
	
	@JsonProperty
	private Long paymentId;
	
	@JsonProperty
	@ManyToOne
	private Account account;

}
