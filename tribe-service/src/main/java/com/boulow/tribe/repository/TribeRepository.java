package com.boulow.tribe.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.boulow.tribe.model.Tribe;

@Repository
public interface TribeRepository extends CrudRepository<Tribe, Long> {

}
