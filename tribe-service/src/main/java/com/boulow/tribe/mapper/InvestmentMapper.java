package com.boulow.tribe.mapper;

import org.mapstruct.Mapper;

import com.boulow.tribe.model.Investment;
import com.boulow.tribe.model.dto.InvestmentDto;

@Mapper(componentModel = "spring")
public interface InvestmentMapper {

	InvestmentDto convertToDto(Investment investment);
	Investment convertFromDto(InvestmentDto investmentDto);
}
