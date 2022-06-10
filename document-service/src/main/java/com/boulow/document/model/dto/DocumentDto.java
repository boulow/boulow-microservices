package com.boulow.document.model.dto;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import com.boulow.document.model.DocumentStatus;
import com.boulow.document.model.DocumentType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DocumentDto {

	private long id;
	private long memberId;
	private long tribeId;
	private String name;
	private DocumentType type;
	private DocumentStatus state;
	private String resourceUri;
	private Date expirationDate;
	private Date uploadDate;
	private boolean isDeleted;
    private MultipartFile content;
	private Date lastRetrievedOn;
	private String lastRetrievedBy;
	private long userId;
}
