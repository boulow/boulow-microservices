package com.boulow.account.service.client;

import java.util.Locale;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.boulow.account.model.dto.PaymentDto;

@FeignClient("account-service")
public interface PaymentFeignClient {

	@PostMapping(value = "/v1/payment/", consumes = "application/json")
	ResponseEntity<PaymentDto> initiatePayment(@RequestHeader(value = "Accept-Language", required = false) Locale locale, @ModelAttribute PaymentDto paymentDto, @RequestHeader("Authorization") String token);
}
