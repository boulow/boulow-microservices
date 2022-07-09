package com.boulow.tribe.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountEvent {

	private Long membershipId;
	private Long tribeId;
	private Long investmentId;
	private Long accountId;
	private String correlationId;
	private AccountEventType type;
	private AccountType acctType;
}
