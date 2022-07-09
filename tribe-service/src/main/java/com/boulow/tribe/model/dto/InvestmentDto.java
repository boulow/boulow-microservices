package com.boulow.tribe.model.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.boulow.tribe.model.InvestmentStatus;
import com.boulow.tribe.model.InvestmentType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InvestmentDto {

	private long id;
    private long tribeId;
	private String name;
	private String shortDesc;
	private InvestmentStatus state;
	private InvestmentType type;
	private Date maturityDate;
	private int term;
	private BigDecimal capital;
	private BigDecimal totalGains;
	private BigDecimal totalDividends;
	private double rateOfReturn;
}
