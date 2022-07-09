package com.boulow.tribe.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@Entity
public class TribeParameter {
	
	@EmbeddedId
	@JsonProperty
	private TribeParamId id;
	
	@ManyToOne
    @MapsId("tribeId")
    @JoinColumn(name = "tribeId")
	@JsonProperty
	private Tribe tribe;
	
	@ManyToOne
    @MapsId("paramId")
    @JoinColumn(name = "paramId")
	@JsonProperty
	private Parameter parameter;
	
	@JsonProperty
	@Column(length = 30)
	private String usrValue;
	
	@JsonProperty
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdateOn;

}
