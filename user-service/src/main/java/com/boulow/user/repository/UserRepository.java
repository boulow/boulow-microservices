package com.boulow.user.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.boulow.user.model.AppUser;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<AppUser, Long> {

	Optional<AppUser> findByEmail(String email);

	List<AppUser> findByFirstNameContainingIgnoreCase(String firstname);

	List<AppUser> findByLastNameContainingIgnoreCase(String lastname);

	List<AppUser> findByIsActiveTrue();

}
