package com.boulow.payment.mapper;

import org.mapstruct.Mapper;

import com.boulow.payment.model.Payment;
import com.boulow.payment.model.dto.PaymentDto;

@Mapper(
        componentModel = "spring"
)
public interface PaymentMapper {

	PaymentDto convertToDto(Payment payment);
	Payment convertFromDto(PaymentDto paymentDto);
}
