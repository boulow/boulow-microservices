package com.boulow.user.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import com.boulow.user.events.Document;

@Service
public class EventPublisherService {
	
	private static final Logger log = LoggerFactory.getLogger(EventPublisherService.class);
	
	@Autowired
    private StreamBridge streamBridge;
	
	public void uploadAvatar(Document doc) {
		log.info("Uploading Avatar to userAvatar-out-0");
		streamBridge.send("userAvatar-out-0", doc);
	}

}
