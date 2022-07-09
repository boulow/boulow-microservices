package com.boulow.tribe.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ParameterDto {

	@JsonProperty
	private long id;
	@JsonProperty
	private String code;
	@JsonProperty
	private String description;
	@JsonProperty
	private String defValue;

}
