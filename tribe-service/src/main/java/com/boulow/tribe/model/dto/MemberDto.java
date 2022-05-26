package com.boulow.tribe.model.dto;

import java.util.Date;

import com.boulow.tribe.model.MemberRole;
import com.boulow.tribe.model.MembershipStatus;
import com.boulow.tribe.model.MembershipType;
import com.boulow.tribe.model.Tribe;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberDto {
	
	private long id;
	private Tribe tribe;
    private long userId;
    private MembershipStatus status;
    private MembershipType type;
	private Date joinedOn;
	private MemberRole role;
	
}
