package com.boulow.user.service;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.boulow.user.model.AppUser;
import com.boulow.user.repository.UserRepository;

@Service("userService")
@Transactional
public class UserService {

	@Autowired
	private MessageSource messages;
	
	@Autowired
	private UserRepository userRepository;
	
	public AppUser findById(Long userId, Locale locale) {
		return userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException(
				String.format(messages.getMessage("user.invalid.argument", null, locale))));
	}

}
