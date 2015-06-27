package com.ylink.cim.manage.domain;

import java.util.Date;

/**
 * ElecBill domain. @author MyEclipse Persistence Tools
 */

public class ElecBill implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
	private String startCreateDate;
	private String endCreateDate;
	private String startChargeDate;
	private String endChargeDate;
	private String buildingNo;
	private String year;

	/** default constructor */
	public ElecBill() {
	}

	/** full constructor */
	public ElecBill(String id, String houseSn, Integer prenum, Integer curnum,
			Integer num, Double amount, Double paidAmt, String state,
			String preRecordDate, String curRecordDate, String createUser,
			Date createDate, Date chargeDate, String chargeUser,
			String recordMonth) {
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

	public Double getAmount() {
		return this.amount;
	}

	public String getBranchNo() {
		return branchNo;
	}

	public String getBuildingNo() {
		return buildingNo;
	}

	public Date getChargeDate() {
		return this.chargeDate;
	}

	public String getChargeUser() {
		return this.chargeUser;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public String getCreateUser() {
		return this.createUser;
	}

	public Integer getCurnum() {
		return this.curnum;
	}

	public String getCurRecordDate() {
		return this.curRecordDate;
	}

	public String getEndChargeDate() {
		return endChargeDate;
	}

	public String getEndCreateDate() {
		return endCreateDate;
	}

	public String getHouseSn() {
		return this.houseSn;
	}

	public String getId() {
		return this.id;
	}

	public Integer getNum() {
		return this.num;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public Double getPaidAmt() {
		return this.paidAmt;
	}

	public Integer getPrenum() {
		return this.prenum;
	}

	public String getPreRecordDate() {
		return this.preRecordDate;
	}

	public Double getPrice() {
		return price;
	}

	public String getRecordMonth() {
		return this.recordMonth;
	}

	// Property accessors

	public String getRemark() {
		return remark;
	}

	public String getStartChargeDate() {
		return startChargeDate;
	}

	public String getStartCreateDate() {
		return startCreateDate;
	}

	public String getState() {
		return this.state;
	}

	public String getYear() {
		return year;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public void setBranchNo(String branchNo) {
		this.branchNo = branchNo;
	}

	// Constructors

	public void setBuildingNo(String buildingNo) {
		this.buildingNo = buildingNo;
	}

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

	public void setCurnum(Integer curnum) {
		this.curnum = curnum;
	}

	public void setCurRecordDate(String curRecordDate) {
		this.curRecordDate = curRecordDate;
	}

	public void setEndChargeDate(String endChargeDate) {
		this.endChargeDate = endChargeDate;
	}

	public void setEndCreateDate(String endCreateDate) {
		this.endCreateDate = endCreateDate;
	}

	public void setHouseSn(String houseSn) {
		this.houseSn = houseSn;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public void setPaidAmt(Double paidAmt) {
		this.paidAmt = paidAmt;
	}

	public void setPrenum(Integer prenum) {
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

	public void setStartChargeDate(String startChargeDate) {
		this.startChargeDate = startChargeDate;
	}

	public void setStartCreateDate(String startCreateDate) {
		this.startCreateDate = startCreateDate;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setYear(String year) {
		this.year = year;
	}

}