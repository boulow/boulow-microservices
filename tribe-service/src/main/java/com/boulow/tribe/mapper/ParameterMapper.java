package com.boulow.tribe.mapper;

import org.mapstruct.Mapper;

import com.boulow.tribe.model.Parameter;
import com.boulow.tribe.model.dto.ParameterDto;

@Mapper(
        componentModel = "spring"
)
public interface ParameterMapper {
	
	ParameterDto convertToDto(Parameter param);
	Parameter convertFromDto(ParameterDto paramDto);
}
