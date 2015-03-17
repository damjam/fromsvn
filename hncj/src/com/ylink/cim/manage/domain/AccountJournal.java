package com.ylink.cim.manage.domain;
// default package

import java.util.Date;

/**
 * AccountJournal entity. @author MyEclipse Persistence Tools
 */

public class AccountJournal implements java.io.Serializable {

	// Fields

	private String id;
	private String tradeType;
	private Double amount;
	private Double prebalance;
	private Double balance;
	private String inoutType;
	private Date createDate;
	private String createUser;
	private String remark;
	private String billId;
	private String branchNo;
	
	public String getBranchNo() {
		return branchNo;
	}



	public void setBranchNo(String branchNo) {
		this.branchNo = branchNo;
	}
	// Constructors
	
	public String getBillId() {
		return billId;
	}

	

	public Double getAmount() {
		return amount;
	}


	public void setAmount(Double amount) {
		this.amount = amount;
	}



	



	public Double getPrebalance() {
		return prebalance;
	}



	public void setPrebalance(Double prebalance) {
		this.prebalance = prebalance;
	}



	public Double getBalance() {
		return balance;
	}



	public void setBalance(Double balance) {
		this.balance = balance;
	}



	public void setBillId(String billId) {
		this.billId = billId;
	}

	/** default constructor */
	public AccountJournal() {
	}

	/** minimal constructor */
	public AccountJournal(String id) {
		this.id = id;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTradeType() {
		return this.tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	

	public String getInoutType() {
		return this.inoutType;
	}

	public void setInoutType(String inoutType) {
		this.inoutType = inoutType;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}