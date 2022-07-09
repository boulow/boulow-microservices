package com.boulow.tribe.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.boulow.tribe.model.Investment;

@Repository
public interface InvestmentRepository extends CrudRepository<Investment, Long>{

}
