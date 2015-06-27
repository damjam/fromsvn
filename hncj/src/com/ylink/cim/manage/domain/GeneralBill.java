package com.ylink.cim.manage.domain;

import java.util.Date;

/**
 * GeneralBill domain. @author MyEclipse Persistence Tools
 */

public class GeneralBill implements java.io.Serializable {

	// Fields

	private String id;
	private Date createDate;
	private String createUser;
	private String chargeUser;
	private Date chargeDate;
	private String tradeType;
	private Double unitPrice;
	private Integer num;
	private Double totalAmt;
	private Double paidAmt;
	private String state;
	private String remark;
	private String payerName;
	private String branchNo;
	private String startChargeDate;
	private String endChargeDate;
	private String keyword;

	/** default constructor */
	public GeneralBill() {
	}

	public String getBranchNo() {
		return branchNo;
	}

	public Date getChargeDate() {
		return chargeDate;
	}

	public String getChargeUser() {
		return this.chargeUser;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public String getCreateUser() {
		return createUser;
	}

	public String getEndChargeDate() {
		return endChargeDate;
	}

	public String getId() {
		return this.id;
	}

	public String getKeyword() {
		return keyword;
	}

	public Integer getNum() {
		return this.num;
	}

	public Double getPaidAmt() {
		return paidAmt;
	}

	public String getPayerName() {
		return payerName;
	}

	public String getRemark() {
		return this.remark;
	}

	public String getStartChargeDate() {
		return startChargeDate;
	}

	public String getState() {
		return this.state;
	}

	public Double getTotalAmt() {
		return this.totalAmt;
	}

	public String getTradeType() {
		return this.tradeType;
	}

	// Property accessors

	public Double getUnitPrice() {
		return this.unitPrice;
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

	public void setEndChargeDate(String endChargeDate) {
		this.endChargeDate = endChargeDate;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public void setPaidAmt(Double paidAmt) {
		this.paidAmt = paidAmt;
	}

	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setStartChargeDate(String startChargeDate) {
		this.startChargeDate = startChargeDate;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setTotalAmt(Double totalAmt) {
		this.totalAmt = totalAmt;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

}