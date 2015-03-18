package com.ylink.cim.manage.domain;
// default package

import java.util.Date;

/**
 * AdRent entity. @author MyEclipse Persistence Tools
 */

public class AdRent implements java.io.Serializable {

	// Fields

	private String id;
	private String beginDate;
	private String endDate;
	private Date createDate;
	private Date chargeDate;
	private String createUser;
	private String chargeUser;
	private String position;
	private Integer num;
	private Double amount;
	private String state;
	private String merchantName;
	private String branchNo;
	private String payerName;
	
	
	public Date getChargeDate() {
		return chargeDate;
	}



	public void setChargeDate(Date chargeDate) {
		this.chargeDate = chargeDate;
	}



	public String getCreateUser() {
		return createUser;
	}



	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}



	public String getPayerName() {
		return payerName;
	}



	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}



	public String getBranchNo() {
		return branchNo;
	}



	public void setBranchNo(String branchNo) {
		this.branchNo = branchNo;
	}
	// Constructors

	/** default constructor */
	public AdRent() {
	}

	

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBeginDate() {
		return this.beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return this.endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getChargeUser() {
		return this.chargeUser;
	}

	public void setChargeUser(String chargeUser) {
		this.chargeUser = chargeUser;
	}

	public String getPosition() {
		return this.position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public Integer getNum() {
		return this.num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Double getAmount() {
		return this.amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getMerchantName() {
		return this.merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

}