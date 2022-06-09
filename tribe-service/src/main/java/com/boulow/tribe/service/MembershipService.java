package com.boulow.tribe.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.boulow.tribe.repository.MemberRepository;

@Service("membershipService")
@Transactional
public class MembershipService {
	
	static final Logger logger = LoggerFactory.getLogger(MembershipService.class);

	@Autowired
	MessageSource messages;

	@Autowired
	private MemberRepository memberRepository;
	
	public boolean exists(Long membershipId) {
		return memberRepository.existsById(membershipId);
	}
}
