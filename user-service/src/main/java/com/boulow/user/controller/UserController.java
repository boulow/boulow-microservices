package com.boulow.user.controller;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.boulow.user.mapper.UserMapper;
import com.boulow.user.model.AppUser;
import com.boulow.user.model.dto.UserSlimDto;
import com.boulow.user.service.UserService;

@RestController
@RequestMapping(value = "v1/user")
public class UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private UserService userService;
	
	@GetMapping(value = "/{userId}")
	public ResponseEntity<UserSlimDto> findById(@PathVariable("userId") Long userId,
			@RequestHeader(value = "Accept-Language", required = false) Locale locale) {
		AppUser appUser = userService.findById(userId, locale);
		return ResponseEntity.ok(userMapper.convertToSlimDto(appUser));
	}

}
