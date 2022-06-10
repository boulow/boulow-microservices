package com.boulow.document.service;

import java.io.File;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.boulow.document.event.DocumentEvent;
import com.boulow.document.exception.ResourceNotFoundException;
import com.boulow.document.exception.UnauthorizedException;
import com.boulow.document.mapper.DocumentMapper;
import com.boulow.document.model.Document;
import com.boulow.document.model.DocumentStatus;
import com.boulow.document.model.DocumentType;
import com.boulow.document.model.dto.DocumentDto;
import com.boulow.document.repository.DocumentRepository;
import com.boulow.document.security.utils.FileUtils;
import com.boulow.document.security.utils.UserContext;
import com.boulow.document.security.utils.UserContextHolder;
import com.boulow.document.service.client.TribeFeignClient;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.bulkhead.annotation.Bulkhead.Type;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

@Service("documentService")
@Transactional
public class DocumentService {
	
	static final Logger logger = LoggerFactory.getLogger(DocumentService.class);
	
	@Autowired
	MessageSource messages;
	
	@Autowired
	private DocumentRepository documentRepository;
	
	@Autowired
	private FileStorageService storageService;
	
	@Autowired
	private DocumentMapper docMapper;
	
	@Autowired
	private FileUtils fileUtils;
	
	@Autowired
	private TribeFeignClient tribeFeignClient;
	
	public Document findById(Long docId, Locale locale) {
		Document doc = documentRepository.findById(docId).orElseThrow(() -> new ResourceNotFoundException("Doc not found"));
		return doc;
	}
	
	public Document saveDocEvent(DocumentEvent doc) {
		Document myDoc = docMapper.convertFromEvent(doc);
		return uploadToCloud(doc.getContent(), myDoc, doc.getType());
	}
	
	public Document uploadToCloud(File file, Document doc, DocumentType type) {
		// Upload to AWS S3
		String resourceUri = storageService.uploadFile(file, type);;
		// Save to DB
		doc.setResourceUri(resourceUri);
		doc.setState(DocumentStatus.SUBMITTED);
		doc = documentRepository.save(doc);
		return doc;	
	}
	
	public Document saveDoc(DocumentDto doc) {
		if(doc.getContent() != null && checkMembershipId(doc.getMemberId()) && checkTribeId(doc.getTribeId())) {
			Document myDoc = docMapper.convertFromDto(doc);
			return uploadToCloud(fileUtils.convertMultiPartToFile(doc.getContent()), myDoc, doc.getType());
		} else
			throw new UnauthorizedException("Invalid Tribe or Member");
	}
	
	@CircuitBreaker(name = "tribeService")
	@Bulkhead(name = "bulkheadTribeService", type = Type.THREADPOOL)
	@Retry(name = "retryTribeService")
	public boolean checkTribeId(Long tribeId) {
		UserContextHolder.getContext();
		return tribeFeignClient.validateTribeId(tribeId, UserContext.getAuthToken());
	}
	
	@CircuitBreaker(name = "tribeService")
	@Bulkhead(name = "bulkheadTribeService", type = Type.THREADPOOL)
	@Retry(name = "retryTribeService")
	public boolean checkMembershipId(Long membershipId) {
		UserContextHolder.getContext();
		return tribeFeignClient.validateMembershipId(membershipId, UserContext.getAuthToken());
	}

}
