package com.ylink.cim.manage.domain;

import java.util.Date;

// default package


/**
 * ElecPrestore entity. @author MyEclipse Persistence Tools
 */

public class IcDeposit implements java.io.Serializable {

	// Fields

	private String id;
	private Double amount;
	private String houseSn;
	private String ownerName;
	private String payerName;
	private Date depositDate;
	private String chargeUser;
	private String payType;
	private String state;
	private String cardType;
    private String branchNo;
	
	public String getBranchNo() {
		return branchNo;
	}



	public void setBranchNo(String branchNo) {
		this.branchNo = branchNo;
	}
	// Constructors

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	/** default constructor */
	public IcDeposit() {
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Double getAmount() {
		return this.amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getHouseSn() {
		return this.houseSn;
	}

	public void setHouseSn(String houseSn) {
		this.houseSn = houseSn;
	}

	public String getOwnerName() {
		return this.ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getPayerName() {
		return this.payerName;
	}

	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}

	
	public Date getDepositDate() {
		return depositDate;
	}


	public void setDepositDate(Date depositDate) {
		this.depositDate = depositDate;
	}



	public String getChargeUser() {
		return this.chargeUser;
	}

	public void setChargeUser(String chargeUser) {
		this.chargeUser = chargeUser;
	}

	public String getPayType() {
		return this.payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

}