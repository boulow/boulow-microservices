package com.boulow.user.mapper;

import org.mapstruct.Mapper;

import com.boulow.user.model.Address;
import com.boulow.user.model.dto.AddressDto;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    AddressDto addressToAddressDto(Address address);
    Address addressDtoToAddress(AddressDto addressDto);
}
