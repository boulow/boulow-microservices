package com.boulow.tribe.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import com.boulow.tribe.event.TribeEvent;

@Service
public class TribeEventsPublisher {
	
	private static final Logger log = LoggerFactory.getLogger(TribeEventsPublisher.class);
	
	@Autowired
    private StreamBridge streamBridge;
	
	public void publishNewTribeEvent(TribeEvent event) {
		log.info("Publish New Tribe event to newTribe-out-0");
		streamBridge.send("newTribe-out-0", event);
	}

}
