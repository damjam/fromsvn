package com.ylink.cim.manage.domain;

import java.util.Date;
// default package


/**
 * GeneralBill entity. @author MyEclipse Persistence Tools
 */

public class GeneralBill implements java.io.Serializable {

	// Fields

	private String id;
	private Date createDate;
	private String chargeUser;
	private String tradeType;
	private Double unitPrice;
	private Integer num;
	private Double totalAmt;
	private Double paidAmt;
	private String state;
	private String remark;
	private String payerName;
	private String branchNo;
	
	public String getBranchNo() {
		return branchNo;
	}



	public void setBranchNo(String branchNo) {
		this.branchNo = branchNo;
	}
	// Constructors

	public String getPayerName() {
		return payerName;
	}


	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}


	public Double getPaidAmt() {
		return paidAmt;
	}


	public void setPaidAmt(Double paidAmt) {
		this.paidAmt = paidAmt;
	}


	/** default constructor */
	public GeneralBill() {
	}

	
	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getTradeType() {
		return this.tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public Double getUnitPrice() {
		return this.unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Integer getNum() {
		return this.num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Double getTotalAmt() {
		return this.totalAmt;
	}

	public void setTotalAmt(Double totalAmt) {
		this.totalAmt = totalAmt;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}