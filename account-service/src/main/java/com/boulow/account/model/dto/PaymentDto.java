package com.boulow.account.model.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.boulow.account.model.PaymentChannel;
import com.boulow.account.model.PaymentStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentDto {
	
	private Long id;
	private BigDecimal amount;
	private String currency;
	private PaymentChannel channel;
	private String origAccNum;
	private String beneAccNum;
	private String bankId;
	private String routing;
	private String cardNumber;
	private Date cardExpiryDate;
	private String nameOnCard;
	private String billingAddr1;
	private String billingAddr2;
	private String billingCity;
	private String billingState;
	private String billingCty;
	private String billingZip;
	private String confirmationNumber;
	private String ccv;
	private String origPhoneNumber;
	private String benePhoneNumber;
	private Long transactionId;
	private Long tribeAcctId;
	private Date paymentDateTime;
	private PaymentStatus state;
	private String rejectionReason;
	private String fiName;

}
