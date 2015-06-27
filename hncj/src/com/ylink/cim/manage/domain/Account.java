package com.ylink.cim.manage.domain;

// default package

import java.util.Date;

/**
 * AccountId domain. @author MyEclipse Persistence Tools
 */

public class Account implements java.io.Serializable {

	// Fields
	private String id;
	private String ownerName;
	private String ownerId;
	private Double balance;
	private Double freezeAmt;
	private Double creditAmt;
	private String createUser;
	private Date createDate;
	private String state;
	private String houseSn;
	private String branchNo;
	private String startCreateDate;
	private String endCreateDate;
	private String acctNo;
	private String year;
	private String type;
	private Double amount;

	/** default constructor */
	public Account() {
	}

	public String getAcctNo() {
		return acctNo;
	}

	public Double getAmount() {
		return amount;
	}

	public Double getBalance() {
		return this.balance;
	}

	public String getBranchNo() {
		return branchNo;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public String getCreateUser() {
		return this.createUser;
	}

	public Double getCreditAmt() {
		return this.creditAmt;
	}

	public String getEndCreateDate() {
		return endCreateDate;
	}

	public Double getFreezeAmt() {
		return this.freezeAmt;
	}

	public String getHouseSn() {
		return houseSn;
	}

	public String getId() {
		return id;
	}

	public String getOwnerId() {
		return this.ownerId;
	}

	public String getOwnerName() {
		return this.ownerName;
	}

	public String getStartCreateDate() {
		return startCreateDate;
	}

	// Property accessors

	public String getState() {
		return this.state;
	}

	public String getType() {
		return type;
	}

	public String getYear() {
		return year;
	}

	public void setAcctNo(String acctNo) {
		this.acctNo = acctNo;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public void setBranchNo(String branchNo) {
		this.branchNo = branchNo;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public void setCreditAmt(Double creditAmt) {
		this.creditAmt = creditAmt;
	}

	public void setEndCreateDate(String endCreateDate) {
		this.endCreateDate = endCreateDate;
	}

	public void setFreezeAmt(Double freezeAmt) {
		this.freezeAmt = freezeAmt;
	}

	public void setHouseSn(String houseSn) {
		this.houseSn = houseSn;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public void setStartCreateDate(String startCreateDate) {
		this.startCreateDate = startCreateDate;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setYear(String year) {
		this.year = year;
	}

}