package com.boulow.tribe.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@Entity
public class Parameter {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty
	private long id;
	
	@Column(length = 3)
	@JsonProperty
	private String code;
	
	@Column(length = 150)
	@JsonProperty
	private String description;
	
	@Column(length = 30)
	@JsonProperty
	private String defValue;
	
	@OneToMany(mappedBy = "parameter")
	private Set<TribeParameter> params;

}
