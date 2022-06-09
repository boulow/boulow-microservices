package com.boulow.account.mapper;

import org.mapstruct.Mapper;

import com.boulow.account.model.Account;
import com.boulow.account.model.dto.AccountDto;

@Mapper(
        componentModel = "spring"
)
public interface AccountMapper {
	
	AccountDto convertToDto(Account account);
	Account convertFromDto(AccountDto accountDto);

}
