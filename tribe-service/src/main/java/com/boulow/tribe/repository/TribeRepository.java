package com.boulow.tribe.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.boulow.tribe.model.Tribe;

@Repository
public interface TribeRepository extends CrudRepository<Tribe, Long> {

	@Query("SELECT t FROM Tribe t JOIN t.members m where m.id = ?1")
	Tribe findByMemberId(Long membershipId);
}
