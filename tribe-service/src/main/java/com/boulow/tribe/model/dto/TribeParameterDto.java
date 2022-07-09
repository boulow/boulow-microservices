package com.boulow.tribe.model.dto;

import java.util.Date;
import com.boulow.tribe.model.Parameter;
import com.boulow.tribe.model.Tribe;
import com.boulow.tribe.model.TribeParamId;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TribeParameterDto {
	
	@JsonProperty
	private TribeParamId id;
	@JsonProperty
	private Tribe tribe;
	@JsonProperty
	private Parameter parameter;
	@JsonProperty
	private String usrValue;
	@JsonProperty
	private Date lastUpdateOn;

}
