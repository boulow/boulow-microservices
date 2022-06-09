package com.boulow.account.mapper;

import org.mapstruct.Mapper;

import com.boulow.account.model.Transaction;
import com.boulow.account.model.dto.TransactionDto;

@Mapper(
        componentModel = "spring",
        uses = {AccountMapper.class}
)
public interface TransactionMapper {
	
	TransactionDto convertToDto(Transaction transaction);
	Transaction convertFromDto(TransactionDto transactionDto);

}
