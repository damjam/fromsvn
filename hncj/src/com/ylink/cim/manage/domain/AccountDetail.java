package com.ylink.cim.manage.domain;

// default package

import java.util.Date;

/**
 * AccountDetailId domain. @author MyEclipse Persistence Tools
 */

public class AccountDetail implements java.io.Serializable {

	// Fields

	private String id;
	private String type;
	private String acctNo;
	private Double amount;
	private Date createDate;
	private String createUser;
	private Double balance;
	private Double freezeAmt;
	private Double creditAmt;
	private String billId;
	private String houseSn;
	private String ownerName;
	private String inoutType;
	private String remark;
	private String branchNo;

	public String getAcctNo() {
		return acctNo;
	}

	public Double getAmount() {
		return this.amount;
	}

	// Constructors

	// Property accessors

	public Double getBalance() {
		return this.balance;
	}

	public String getBillId() {
		return this.billId;
	}

	public String getBranchNo() {
		return branchNo;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public String getCreateUser() {
		return this.createUser;
	}

	public Double getCreditAmt() {
		return this.creditAmt;
	}

	public Double getFreezeAmt() {
		return this.freezeAmt;
	}

	public String getHouseSn() {
		return this.houseSn;
	}

	public String getId() {
		return this.id;
	}

	public String getInoutType() {
		return inoutType;
	}

	public String getOwnerName() {
		return this.ownerName;
	}

	public String getRemark() {
		return remark;
	}

	public String getType() {
		return this.type;
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

	public void setBillId(String billId) {
		this.billId = billId;
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

	public void setFreezeAmt(Double freezeAmt) {
		this.freezeAmt = freezeAmt;
	}

	public void setHouseSn(String houseSn) {
		this.houseSn = houseSn;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setInoutType(String inoutType) {
		this.inoutType = inoutType;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setType(String type) {
		this.type = type;
	}

}