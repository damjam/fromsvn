package com.ylink.cim.manage.domain;

import java.util.Date;

// default package

/**
 * ElecPrestore domain. @author MyEclipse Persistence Tools
 */

public class IcDeposit implements java.io.Serializable {

	// Fields

	private String id;
	private Double amount;
	private String houseSn;
	private String ownerName;
	private String payerName;
	private Date chargeDate;
	private String chargeUser;
	private String createUser;
	private Date createDate;
	private String payType;
	private String state;
	private String cardType;
	private String branchNo;
	private String remark;
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	/** default constructor */
	public IcDeposit() {
	}

	public Double getAmount() {
		return this.amount;
	}

	// Constructors

	public String getBranchNo() {
		return branchNo;
	}

	public String getCardType() {
		return cardType;
	}

	public Date getChargeDate() {
		return chargeDate;
	}

	// Property accessors

	public String getChargeUser() {
		return this.chargeUser;
	}


	public String getCreateUser() {
		return createUser;
	}

	public String getHouseSn() {
		return this.houseSn;
	}

	public String getId() {
		return this.id;
	}

	public String getOwnerName() {
		return this.ownerName;
	}

	public String getPayerName() {
		return this.payerName;
	}

	public String getPayType() {
		return this.payType;
	}

	public String getState() {
		return this.state;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public void setBranchNo(String branchNo) {
		this.branchNo = branchNo;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public void setChargeDate(Date chargeDate) {
		this.chargeDate = chargeDate;
	}

	public void setChargeUser(String chargeUser) {
		this.chargeUser = chargeUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public void setHouseSn(String houseSn) {
		this.houseSn = houseSn;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
}