package com.boulow.user.events;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.boulow.user.events.model.Document;

@Service
public class DocUploadPubService {
	
	private static final Logger log = LoggerFactory.getLogger(DocUploadPubService.class);
	
	@Autowired
    private StreamBridge streamBridge;
	
	public void uploadAvatar(Document doc) {
		log.info("Uploading Avatar to userAvatar-out-0");
		streamBridge.send("userAvatar-out-0", doc);
	}
	
	public File multipartToFile(MultipartFile multipart, String fileName) throws IllegalStateException, IOException {
	    File convFile = new File(System.getProperty("java.io.tmpdir")+"/"+fileName);
	    multipart.transferTo(convFile);
	    return convFile;
	}

}
