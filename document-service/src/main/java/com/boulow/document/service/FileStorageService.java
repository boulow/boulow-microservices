package com.boulow.document.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.boulow.document.config.BoulowProperties;
import com.boulow.document.model.DocumentType;
import com.boulow.document.security.utils.DocUtils;

@Service("fileStorage-service")
public class FileStorageService {
	
	private static final Logger log = LoggerFactory.getLogger(FileStorageService.class);

    @Autowired
    private AmazonS3 s3client;
    
    @Autowired
    private DocUtils docUtils;
    
    @Autowired
    BoulowProperties boulowProperties;
    
    @Async
    public String uploadFile(File file, DocumentType type) {
    	String fileUrl = "";
    	try {
            String fileName = docUtils.getDestinationFolder(type) + "/" + docUtils.generateResourceName(file.getName());
            s3client.putObject(new PutObjectRequest(boulowProperties.getAmazonS3Props().getBucketName(), fileName, file)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
            fileUrl = generateObjectUrl(fileName);
            Files.delete(file.toPath());
        } catch (AmazonServiceException e) {
        	log.error("Error {} occurred while uploading file", e.getLocalizedMessage());
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return fileUrl;
    }
    
    public String generateObjectUrl(String filename) {
    	return String.format("https://%s.s3.amazonaws.com/%s", boulowProperties.getAmazonS3Props().getBucketName(), filename); 
    }

}
