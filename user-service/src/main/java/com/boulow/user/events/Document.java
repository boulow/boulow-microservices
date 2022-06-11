package com.boulow.user.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Document {
	
	private String correlationId;
	private Long userId;
	private Long tribeId;
	private DocumentType type;
	private byte[] content;
	private String name;
	private String awsPath;

}
