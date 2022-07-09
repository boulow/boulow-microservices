package com.boulow.user.service;

import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.boulow.user.events.Document;

import lombok.AllArgsConstructor;

@Component("avatarReadyEventConsumer")
@AllArgsConstructor
public class AvatarReadyEventConsumer implements Consumer<Document> {
	
	static final Logger logger = LoggerFactory.getLogger(AvatarReadyEventConsumer.class);
	
	@Autowired
	private UserService userService;
	
	@Override
	public void accept(Document doc) {
		logger.info("Updating user # %s avatar's url", doc.getUserId());
		userService.updateAvatarUrl(doc.getUserId(), doc.getAwsPath());
		
	}

}
