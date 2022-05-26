package com.boulow.user.mapper;

import org.mapstruct.Mapper;

import com.boulow.user.model.AppUser;
import com.boulow.user.model.dto.UserDto;
import com.boulow.user.model.dto.UserSlimDto;

@Mapper(
        componentModel = "spring",
        uses = {AddressMapper.class}
)
public interface UserMapper {

    UserDto convertToDto(AppUser appUser);
    UserSlimDto convertToSlimDto(AppUser appUser);
    AppUser convertFromDto(UserDto UserDTO);
}
