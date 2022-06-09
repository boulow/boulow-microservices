package com.boulow.account.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.boulow.account.model.Account;
import com.boulow.account.model.Payment;

@Repository
public interface PaymentRepository extends CrudRepository<Payment, Long> {

	Page<Payment> findByAccount(Account acct, Pageable pageRequest);
	
	Page<Payment> findByAccountAndOriginator(Account acct, String originatorName, Pageable pageRequest);
	
	Page<Payment> findByAccountAndBeneficiary(Account acct, String beneficiaryName, Pageable pageRequest);
}
