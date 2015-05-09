package com.ylink.cim.manage.domain;

import java.util.Date;

// default package

/**
 * WaterBillId entity. @author MyEclipse Persistence Tools
 */

public class WaterBill implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String houseSn;
	private Long prenum;
	private Long curnum;
	private Long num;
	private Double amount;
	private String state;
	private String preRecordDate;
	private String curRecordDate;
	private String createUser;
	private Date createDate;
	private String chargeUser;
	private Date chargeDate;
	private String recordMonth;
	private Double price;
	private String ownerName;
	private String remark;
	private Double balance;
	private Double paidAmt;
	private String mobile;
	
	// Constructors
	
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Double getPaidAmt() {
		if (paidAmt == null) {
			paidAmt = 0d;
		}
		return paidAmt;
	}

	public void setPaidAmt(Double paidAmt) {
		this.paidAmt = paidAmt;
	}

	public Double getBalance() {
		if (balance == null) {
			balance = 0d;
		}
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Double getPrice() {
		return price;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getRecordMonth() {
		return recordMonth;
	}

	public void setRecordMonth(String recordMonth) {
		this.recordMonth = recordMonth;
	}

	/** default constructor */
	public WaterBill() {
	}

	/** minimal constructor */
	public WaterBill(String id) {
		this.id = id;
	}


	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}
	

	public String getHouseSn() {
		return houseSn;
	}

	public void setHouseSn(String houseSn) {
		this.houseSn = houseSn;
	}

	public Long getPrenum() {
		return this.prenum;
	}

	public void setPrenum(Long prenum) {
		this.prenum = prenum;
	}

	public Long getCurnum() {
		return this.curnum;
	}

	public void setCurnum(Long curnum) {
		this.curnum = curnum;
	}

	public Long getNum() {
		return this.num;
	}

	public void setNum(Long num) {
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

	public String getPreRecordDate() {
		return preRecordDate;
	}

	public void setPreRecordDate(String preRecordDate) {
		this.preRecordDate = preRecordDate;
	}

	public String getCurRecordDate() {
		return curRecordDate;
	}

	public void setCurRecordDate(String curRecordDate) {
		this.curRecordDate = curRecordDate;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getChargeUser() {
		return chargeUser;
	}

	public void setChargeUser(String chargeUser) {
		this.chargeUser = chargeUser;
	}

	public Date getChargeDate() {
		return chargeDate;
	}

	public void setChargeDate(Date chargeDate) {
		this.chargeDate = chargeDate;
	}


}