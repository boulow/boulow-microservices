package com.boulow.tribe.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.boulow.tribe.service.MembershipService;

@RestController
@RequestMapping(value = "v1/mtg/mbr")
public class MembershipController {

	static final Logger logger = LoggerFactory.getLogger(MembershipController.class);
	
	@Autowired
	private MembershipService membershipService;
	
	@GetMapping(value = "/exists/{membershipId}")
	public ResponseEntity<Boolean> membershipExists(@PathVariable("membershipId") Long membershipId) {
		return ResponseEntity.ok(membershipService.exists(membershipId));
	}
}
