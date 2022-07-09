package com.boulow.tribe.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import com.boulow.tribe.model.dto.UserSlimDto;

@FeignClient("user-service")
public interface UserFeignClient {

	@GetMapping(value = "/v1/usr/{userId}", consumes = "application/json")
	UserSlimDto getUser(@PathVariable("userId") Long userId, @RequestHeader("Authorization") String token);
}
