package com.boulow.document.event;

import com.boulow.document.model.DocumentType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentEvent {
	
	private String correlationId;
	private Long memberId;
	private Long tribeId;
	private DocumentType type;
	private byte[] content;
	private String name;
	private String resourceUri;
	private Long userId;

}
