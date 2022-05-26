package com.boulow.tribe.mapper;

import org.mapstruct.Mapper;

import com.boulow.tribe.model.Address;
import com.boulow.tribe.model.dto.AddressDto;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    AddressDto addressToAddressDto(Address address);
    Address addressDtoToAddress(AddressDto addressDto);
}
