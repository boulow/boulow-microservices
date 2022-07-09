package com.boulow.payment.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.boulow.payment.model.Payment;

@Repository
public interface PaymentRepository extends CrudRepository<Payment, Long> {

	Page<Payment> findByAcctId(Long acctId, Pageable pageRequest);
	
	Page<Payment> findByAcctIdAndOriginator(Long acctId, String originatorName, Pageable pageRequest);
	
	Page<Payment> findByAcctIdAndBeneficiary(Long acctId, String beneficiaryName, Pageable pageRequest);
}
