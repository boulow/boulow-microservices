package com.boulow.tribe.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.boulow.tribe.model.Member;
import com.boulow.tribe.model.Tribe;

@Repository
public interface MemberRepository extends CrudRepository<Member, Long>  {
	
	Member findByUserIdAndTribe(Long userId, Tribe tribe);
	
	List<Member> findByUserId(Long userId);

}
