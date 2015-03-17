package com.ylink.cim.manage.domain;
// default package

import java.util.Date;

/**
 * AccountId entity. @author MyEclipse Persistence Tools
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
	
	public String getBranchNo() {
		return branchNo;
	}



	public void setBranchNo(String branchNo) {
		this.branchNo = branchNo;
	}



	/** default constructor */
	public Account() {
	}

	
	
	// Property accessors

	public String getHouseSn() {
		return houseSn;
	}



	public void setHouseSn(String houseSn) {
		this.houseSn = houseSn;
	}
	
	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public String getOwnerName() {
		return this.ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getOwnerId() {
		return this.ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public Double getBalance() {
		return this.balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public Double getFreezeAmt() {
		return this.freezeAmt;
	}

	public void setFreezeAmt(Double freezeAmt) {
		this.freezeAmt = freezeAmt;
	}

	public Double getCreditAmt() {
		return this.creditAmt;
	}

	public void setCreditAmt(Double creditAmt) {
		this.creditAmt = creditAmt;
	}

	public String getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}


}