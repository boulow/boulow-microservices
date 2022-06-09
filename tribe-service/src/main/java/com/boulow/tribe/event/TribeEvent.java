package com.boulow.tribe.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TribeEvent {
	
	private Long membershipId;
	private Long tribeId;
	private String correlationId;

}
