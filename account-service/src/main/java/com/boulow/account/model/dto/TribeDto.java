package com.boulow.account.model.dto;

import java.math.BigDecimal;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TribeDto {
	
	private long id;
	private long userId;
	private String name;
	private String shortDesc;
	private MultipartFile logo;
	private Address address;
	private int membersCnt;
    private Visibility visibility;
	private BigDecimal minContribution;
	private String acctNum;
	private String phoneNumber;
	private String routing;
	private String fiName;

}
