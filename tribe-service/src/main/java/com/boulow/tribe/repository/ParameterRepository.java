package com.boulow.tribe.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.boulow.tribe.model.Parameter;

@Repository
public interface ParameterRepository extends CrudRepository<Parameter, Long> {

}
