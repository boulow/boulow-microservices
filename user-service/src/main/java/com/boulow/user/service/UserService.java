package com.boulow.user.service;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.boulow.user.events.DocUploadPubService;
import com.boulow.user.events.model.Document;
import com.boulow.user.events.model.DocumentType;
import com.boulow.user.exception.CustomException;
import com.boulow.user.exception.UnauthorizedException;
import com.boulow.user.model.AppUser;
import com.boulow.user.model.dto.UserUpdateDto;
import com.boulow.user.repository.UserRepository;

@Service("userService")
@Transactional
public class UserService {

	@Autowired
	private MessageSource messages;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private DocUploadPubService pubService;
	
	private static final Logger log = LoggerFactory.getLogger(UserService.class);
	
	public AppUser findById(Long userId, Locale locale) {
		return userRepository.findById(userId).orElseThrow(() -> new CustomException(
				String.format(messages.getMessage("user.invalid.argument", null, locale))));
	}
	
	public List<AppUser> findAll() {
		return (List<AppUser>) userRepository.findAll();
	}

	public Optional<AppUser> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	public List<AppUser> findByFirstNameContainingIgnoreCase(String firstname) {
		return userRepository.findByFirstNameContainingIgnoreCase(firstname);
	}

	public List<AppUser> findByLastNameContainingIgnoreCase(String lastname) {
		return userRepository.findByLastNameContainingIgnoreCase(lastname);
	}

	public List<AppUser> findByIsActiveTrue() {
		return userRepository.findByIsActiveTrue();
	}

	public AppUser save(AppUser user) {
		return userRepository.save(user);
	}
	
	public void delete(Long id) {
		userRepository.deleteById(id);
	}
	
	public AppUser update(UserUpdateDto toUser, Locale locale) {
		AppUser user = this.findByEmail(toUser.getEmail()).orElse(null);
		log.info("User found in DB");
		if(user != null) {
			user = userRepository.save(user);
			//TODO Publish Document upload event to AWS for the avatar to Kafka
			if(toUser.getAvatar() != null) {
				log.info("Publishing user avatar upload event to queue");
				Document avatar = new Document();
				try {
					avatar.setContent(pubService.multipartToFile(toUser.getAvatar(), toUser.getAvatar().getName()));
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				avatar.setUserId(toUser.getId());
				avatar.setType(DocumentType.AVATAR);
				avatar.setName(toUser.getAvatar().getOriginalFilename());
				pubService.uploadAvatar(avatar);
			}
			
		} else 
			throw new UnauthorizedException(String.format(messages.getMessage("user.unauthorized.operation", null, locale)));

		return user;
	}

}
