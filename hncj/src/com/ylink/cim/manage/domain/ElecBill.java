package com.ylink.cim.manage.domain;

import java.util.Date;
// default package


/**
 * ElecBill entity. @author MyEclipse Persistence Tools
 */

public class ElecBill implements java.io.Serializable {

	// Fields

	private String id;
	private String houseSn;
	private Integer prenum;
	private Integer curnum;
	private Integer num;
	private Double amount;
	private Double paidAmt;
	private String state;
	private String preRecordDate;
	private String curRecordDate;
	private String createUser;
	private Date createDate;
	private Date chargeDate;
	private String chargeUser;
	private String recordMonth;
	private Double price;
	private String remark;
	private String ownerName;
	private String branchNo;
	
	public String getBranchNo() {
		return branchNo;
	}



	public void setBranchNo(String branchNo) {
		this.branchNo = branchNo;
	}
	// Constructors

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
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

	public void setPrice(Double price) {
		this.price = price;
	}

	/** default constructor */
	public ElecBill() {
	}

	/** full constructor */
	public ElecBill(String id, String houseSn, Integer prenum, Integer curnum, Integer num, Double amount,
			Double paidAmt, String state, String preRecordDate, String curRecordDate, String createUser,
			Date createDate, Date chargeDate, String chargeUser, String recordMonth) {
		this.id = id;
		this.houseSn = houseSn;
		this.prenum = prenum;
		this.curnum = curnum;
		this.num = num;
		this.amount = amount;
		this.paidAmt = paidAmt;
		this.state = state;
		this.preRecordDate = preRecordDate;
		this.curRecordDate = curRecordDate;
		this.createUser = createUser;
		this.createDate = createDate;
		this.chargeDate = chargeDate;
		this.chargeUser = chargeUser;
		this.recordMonth = recordMonth;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHouseSn() {
		return this.houseSn;
	}

	public void setHouseSn(String houseSn) {
		this.houseSn = houseSn;
	}

	public Integer getPrenum() {
		return this.prenum;
	}

	public void setPrenum(Integer prenum) {
		this.prenum = prenum;
	}

	public Integer getCurnum() {
		return this.curnum;
	}

	public void setCurnum(Integer curnum) {
		this.curnum = curnum;
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

	public Double getPaidAmt() {
		return this.paidAmt;
	}

	public void setPaidAmt(Double paidAmt) {
		this.paidAmt = paidAmt;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPreRecordDate() {
		return this.preRecordDate;
	}

	public void setPreRecordDate(String preRecordDate) {
		this.preRecordDate = preRecordDate;
	}

	public String getCurRecordDate() {
		return this.curRecordDate;
	}

	public void setCurRecordDate(String curRecordDate) {
		this.curRecordDate = curRecordDate;
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

	public Date getChargeDate() {
		return this.chargeDate;
	}

	public void setChargeDate(Date chargeDate) {
		this.chargeDate = chargeDate;
	}

	public String getChargeUser() {
		return this.chargeUser;
	}

	public void setChargeUser(String chargeUser) {
		this.chargeUser = chargeUser;
	}

	public String getRecordMonth() {
		return this.recordMonth;
	}

	public void setRecordMonth(String recordMonth) {
		this.recordMonth = recordMonth;
	}

}