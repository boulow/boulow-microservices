package com.boulow.account.mapper;

import org.mapstruct.Mapper;

import com.boulow.account.model.Payment;
import com.boulow.account.model.dto.PaymentDto;

@Mapper(
        componentModel = "spring",
        uses = {AccountMapper.class}
)
public interface PaymentMapper {

	PaymentDto convertToDto(Payment payment);
	Payment convertFromDto(PaymentDto paymentDto);
}
