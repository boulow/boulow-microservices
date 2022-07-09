package com.boulow.account.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import com.boulow.account.model.dto.TribeDto;

@FeignClient("tribe-service")
public interface TribeFeignClient {

	@GetMapping(value = "/v1/mtg/exists/{tribeId}", consumes = "application/json")
	boolean validateTribeId(@PathVariable("tribeId") Long tribeId, @RequestHeader("Authorization") String token);
	
	@GetMapping(value = "/v1/mtg/membership-{memberId}", consumes = "application/json")
	TribeDto getTribeByMemberId(@PathVariable("memberId") Long memberId, @RequestHeader("Authorization") String token);
	
	@GetMapping(value = "/v1/mtg/mbr/exists/{membershipId}", consumes = "application/json")
	boolean validateMembershipId(@PathVariable("membershipId") Long membershipId, @RequestHeader("Authorization") String token);
}
