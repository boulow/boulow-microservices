package com.boulow.document.mapper;

import org.mapstruct.Mapper;

import com.boulow.document.event.DocumentEvent;
import com.boulow.document.model.Document;
import com.boulow.document.model.dto.DocumentDto;

@Mapper(
        componentModel = "spring"
)
public interface DocumentMapper {
	
	DocumentDto convertToDto(Document doc);
	Document convertFromDto(DocumentDto docDto);
	Document convertFromEvent(DocumentEvent docEvent);

}
