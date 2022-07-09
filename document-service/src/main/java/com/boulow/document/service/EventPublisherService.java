package com.boulow.document.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import com.boulow.document.model.Document;

@Service
public class EventPublisherService {

private static final Logger log = LoggerFactory.getLogger(EventPublisherService.class);
	
	@Autowired
    private StreamBridge streamBridge;
	
	public void updateUserAvatarUrl(Document doc) {
		log.info("Notifying user service that avatar has been uploaded to AWS S3");
		streamBridge.send("avatarReady-out-0", doc);
	}
}
