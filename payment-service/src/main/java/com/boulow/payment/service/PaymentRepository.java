package com.boulow.payment.service;

import org.springframework.data.repository.CrudRepository;

import com.boulow.payment.model.Payment;

public interface PaymentRepository extends CrudRepository<Payment, Long> {

}
