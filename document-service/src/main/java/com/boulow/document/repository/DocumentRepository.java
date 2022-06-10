package com.boulow.document.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.boulow.document.model.Document;
import com.boulow.document.model.DocumentStatus;
import com.boulow.document.model.DocumentType;

public interface DocumentRepository extends CrudRepository<Document, Long> {

	List<Document> findByMemberIdAndTribeId(long memberId, long tribeId);
	
	List<Document> findByNameContaining(String pattern);
	
	List<Document> findByMemberIdAndTribeIdAndState(long memberId, long tribeId, DocumentStatus state);
	
	List<Document> findByMemberIdAndTribeIdAndType(long memberId, long tribeId, DocumentType type);
}
