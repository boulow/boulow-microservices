package com.boulow.account.model.dto;

import java.math.BigDecimal;
import java.util.Date;
import com.boulow.account.model.Account;
import com.boulow.account.model.PaymentChannel;
import com.boulow.account.model.PaymentStatus;
import com.boulow.account.model.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentDto {

	private long id;
	private Account account;
	private BigDecimal availableBal;
	private Date paymentDate;
    private PaymentStatus status;
	private PaymentChannel channel;
	private PaymentType type;
	private String originator;
	private String beneficiary;
	private String origFiName;
	private String origFiAccNum;
	private String beneFiName;
	private String beneFiAccNum;
}
