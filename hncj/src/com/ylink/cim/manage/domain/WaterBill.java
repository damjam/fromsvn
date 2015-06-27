package com.ylink.cim.manage.domain;

import java.util.Date;

// default package

/**
 * WaterBillId domain. @author MyEclipse Persistence Tools
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
	private String branchNo;
	private String startCreateDate;
	private String endCreateDate;
	private String buildingNo;
	private String year;
	//±íµ¥ÔªËØ
	
	public String getBuildingNo() {
		return buildingNo;
	}



	public void setBuildingNo(String buildingNo) {
		this.buildingNo = buildingNo;
	}



	public String getYear() {
		return year;
	}



	public void setYear(String year) {
		this.year = year;
	}



	public String getStartCreateDate() {
		return startCreateDate;
	}



	public void setStartCreateDate(String startCreateDate) {
		this.startCreateDate = startCreateDate;
	}



	public String getEndCreateDate() {
		return endCreateDate;
	}



	public void setEndCreateDate(String endCreateDate) {
		this.endCreateDate = endCreateDate;
	}



	/** default constructor */
	public WaterBill() {
	}



	/** minimal constructor */
	public WaterBill(String id) {
		this.id = id;
	}
	
	public Double getAmount() {
		return this.amount;
	}

	public Double getBalance() {
		if (balance == null) {
			balance = 0d;
		}
		return balance;
	}

	public String getBranchNo() {
		return branchNo;
	}

	public Date getChargeDate() {
		return chargeDate;
	}

	public String getChargeUser() {
		return chargeUser;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public String getCreateUser() {
		return createUser;
	}

	public Long getCurnum() {
		return this.curnum;
	}

	public String getCurRecordDate() {
		return curRecordDate;
	}

	public String getHouseSn() {
		return houseSn;
	}

	public String getId() {
		return this.id;
	}

	public Long getNum() {
		return this.num;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public Double getPaidAmt() {
		if (paidAmt == null) {
			paidAmt = 0d;
		}
		return paidAmt;
	}


	// Property accessors

	public Long getPrenum() {
		return this.prenum;
	}

	public String getPreRecordDate() {
		return preRecordDate;
	}
	

	public Double getPrice() {
		return price;
	}

	public String getRecordMonth() {
		return recordMonth;
	}

	public String getRemark() {
		return remark;
	}

	public String getState() {
		return this.state;
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
	// Constructors

	public void setChargeDate(Date chargeDate) {
		this.chargeDate = chargeDate;
	}

	public void setChargeUser(String chargeUser) {
		this.chargeUser = chargeUser;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public void setCurnum(Long curnum) {
		this.curnum = curnum;
	}

	public void setCurRecordDate(String curRecordDate) {
		this.curRecordDate = curRecordDate;
	}

	public void setHouseSn(String houseSn) {
		this.houseSn = houseSn;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setNum(Long num) {
		this.num = num;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public void setPaidAmt(Double paidAmt) {
		this.paidAmt = paidAmt;
	}

	public void setPrenum(Long prenum) {
		this.prenum = prenum;
	}

	public void setPreRecordDate(String preRecordDate) {
		this.preRecordDate = preRecordDate;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public void setRecordMonth(String recordMonth) {
		this.recordMonth = recordMonth;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setState(String state) {
		this.state = state;
	}


}