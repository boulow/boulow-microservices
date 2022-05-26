package com.boulow.user.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.boulow.user.model.Address;

@Repository
public interface AddressRepository extends CrudRepository<Address, Long> {

}
