package com.boulow.tribe.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MembershipDto {
	
	private Long userId;
	private Long tribeId;

}
