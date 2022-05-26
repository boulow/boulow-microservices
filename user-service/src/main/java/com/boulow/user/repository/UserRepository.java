package com.boulow.user.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.boulow.user.model.AppUser;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<AppUser, Long> {

    Optional<AppUser> findByEmail(String email);
    
    Optional<AppUser> findByUsername(String username);

    Iterable<AppUser> findByFirstNameContainingIgnoreCase(String firstName);

    Iterable<AppUser> findByLastNameContainingIgnoreCase(String lastName);

    Optional<AppUser> findByPhone(String phone);

}
