package com.boulow.tribe.mapper;

import org.mapstruct.Mapper;

import com.boulow.tribe.model.Tribe;
import com.boulow.tribe.model.dto.TribeDto;

@Mapper(
        componentModel = "spring",
        uses = {AddressMapper.class}
)
public interface TribeMapper {
	
	TribeDto convertToDto(Tribe tribe);
    Tribe convertFromDto(TribeDto tribeDto);

}
