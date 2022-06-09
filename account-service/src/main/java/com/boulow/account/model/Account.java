package com.boulow.account.model;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@Entity
@Table(name = "app_account")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "acctType", length = 3)
public class Account {
	
	public Account(String type) {
		super();
		this.availableBalance = new BigDecimal("0");
		this.accountNumber = type + this.generateAccountNum();
		this.state = AccountState.OPEN;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty
	private long id;
	
	@JsonProperty
	private long membershipId;
	
	public static SimpleDateFormat dft = new SimpleDateFormat("yyMMddhhmmssMs");
	
	@JsonProperty
	private BigDecimal availableBalance;
	
	@JsonProperty
	@Column(length = 50)
	private String accountNumber;
	
	@JsonProperty
	@Column(name = "acctType", insertable = false, updatable = false, length =3)
	private String acctType;
	
	@JsonProperty
	@Temporal(TemporalType.TIMESTAMP)
	private Date closeDate;
	
	@JsonProperty
	@Temporal(TemporalType.TIMESTAMP)
	private Date nextStmt;
	
	@JsonProperty
	@Enumerated(EnumType.ORDINAL)
    private AccountState state;
	
	@OneToMany(
	        mappedBy = "account",
	        cascade = CascadeType.ALL,
	        orphanRemoval = true,
	        fetch = FetchType.LAZY
	    )
	private List<Payment> payments = new ArrayList<>();
	
	@OneToMany(
	        mappedBy = "account",
	        cascade = CascadeType.ALL,
	        orphanRemoval = true,
	        fetch = FetchType.LAZY
	    )
	private List<Transaction> transactions = new ArrayList<>();
	
	public void addPayment(Payment payment) {
		payments.add(payment);
		payment.setAccount(this);
    }
 
    public void removePayment(Payment payment) {
    	payments.remove(payment);
    	payment.setAccount(null);
    }
    
    public void addTransaction(Transaction transaction) {
    	transactions.add(transaction);
    	transaction.setAccount(this);
    }
 
    public void removeTransaction(Transaction transaction) {
    	transactions.remove(transaction);
    	transaction.setAccount(null);
    }

    public String generateAccountNum() {
		Date dNow = new Date();
        return dft.format(dNow) + this.getMembershipId();
    }
}
