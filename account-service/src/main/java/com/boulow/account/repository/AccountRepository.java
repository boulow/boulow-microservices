package com.boulow.account.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.boulow.account.model.Account;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {

	Optional<Account> findByAccountNumber(String acctNum);
	
	List<Account> findByMembershipId(Long id);
	
	List<Account> findByAcctTypeAndMembershipId(String accType, Long id);
	
	Account findByTribeIdAndAcctType(Long tribeId, String accType);
}
