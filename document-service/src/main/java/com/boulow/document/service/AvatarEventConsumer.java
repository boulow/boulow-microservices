package com.boulow.document.service;

import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.boulow.document.event.DocumentEvent;

import lombok.AllArgsConstructor;

@Component("avatarEventConsumer")
@AllArgsConstructor
public class AvatarEventConsumer implements Consumer<DocumentEvent> {
	
	static final Logger logger = LoggerFactory.getLogger(AvatarEventConsumer.class);
	
	@Autowired
	private DocumentService docService;
	
	@Override
	public void accept(DocumentEvent doc) {
		logger.info("Uploading Avatar *******");
		docService.saveDocEvent(doc);
		
	}

}
