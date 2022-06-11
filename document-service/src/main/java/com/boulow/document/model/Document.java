package com.boulow.document.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@Entity
public class Document {
	
	public Document() {
		this.uploadDate = new Date();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty
	private long id;
	
	@JsonProperty
	private long memberId;
	
	@JsonProperty
	private long tribeId;
	
	@JsonProperty
	private long userId;
	
	@JsonProperty
	@Column(unique = true, length = 80)
	private String name;
	
	@JsonProperty
	@Enumerated(EnumType.STRING)
	private DocumentType type;
	
	@JsonProperty
	@Enumerated(EnumType.STRING)
	private DocumentStatus state;
	
	@JsonProperty
	private String resourceUri;
	
	@JsonProperty
	@Temporal(TemporalType.TIMESTAMP)
	private Date expirationDate;
	
	@JsonProperty
	@Temporal(TemporalType.TIMESTAMP)
	private Date uploadDate;
	
	@JsonProperty
	private boolean isDeleted;
	
	@JsonProperty
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastRetrievedOn;
	
	@JsonProperty
	private String lastRetrievedBy;
	
}
