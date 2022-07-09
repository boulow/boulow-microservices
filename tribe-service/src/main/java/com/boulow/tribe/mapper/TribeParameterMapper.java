package com.boulow.tribe.mapper;

import org.mapstruct.Mapper;

import com.boulow.tribe.model.TribeParameter;
import com.boulow.tribe.model.dto.TribeParameterDto;

@Mapper(
        componentModel = "spring",
        uses = {TribeMapper.class, ParameterMapper.class}
)
public interface TribeParameterMapper {

	TribeParameterDto convertToDto(TribeParameter tribeParam);
	TribeParameter convertFromDto(TribeParameterDto tribeParamDto);
}
