package com.boulow.tribe.model.dto;

import java.math.BigDecimal;
import java.util.List;

import com.boulow.tribe.model.Address;
import com.boulow.tribe.model.Member;
import com.boulow.tribe.model.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TribeDto {
	
	private long id;
	private long userId;
	private String name;
	private String shortDesc;
	private byte[] logo;
	private Address address;
	@JsonIgnore
	private List<Member> members;
	private int membersCnt;
    private Visibility visibility;
	private BigDecimal minContribution;
	private String acctNum;
	private String routing;
	private String fiName;

}
