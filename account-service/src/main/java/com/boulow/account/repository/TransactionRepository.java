package com.boulow.account.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.boulow.account.model.Account;
import com.boulow.account.model.Transaction;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {
	
	Page<Transaction> findByAccount(Account acct, Pageable pageRequest);
	
	Page<Transaction> findByAccountAndOriginatorName(Account acct, String originatorName, Pageable pageRequest);

}
