package com.ylink.cim.manage.domain;
// default package

import java.util.Date;

/**
 * DepositBillId domain. @author MyEclipse Persistence Tools
 */

public class DepositBill implements java.io.Serializable {

	// Fields

	private String id;
	private String houseSn;
	private Double amount;
	private String state;
	private String ownerId;
	private Date depositDate;
	private String depositUser;
	private Date refundDate;
	private String refundUser;
	private Double refundAmount;
	private String purpose;
	private String payerName;
	private String remark;
	private String branchNo;
	
	public String getBranchNo() {
		return branchNo;
	}



	public void setBranchNo(String branchNo) {
		this.branchNo = branchNo;
	}
	// Constructors

	
	

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPayerName() {
		return payerName;
	}

	public void setPayerName(String payerName) {
		this.payerName = payerName;
	}

	/** default constructor */
	public DepositBill() {
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public Double getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(Double refundAmount) {
		this.refundAmount = refundAmount;
	}

	/** minimal constructor */
	public DepositBill(String id) {
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
		return this.houseSn;
	}

	public void setHouseSn(String houseSn) {
		this.houseSn = houseSn;
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


	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public Date getDepositDate() {
		return this.depositDate;
	}

	public void setDepositDate(Date depositDate) {
		this.depositDate = depositDate;
	}

	public String getDepositUser() {
		return this.depositUser;
	}

	public void setDepositUser(String depositUser) {
		this.depositUser = depositUser;
	}

	public Date getRefundDate() {
		return refundDate;
	}

	public void setRefundDate(Date refundDate) {
		this.refundDate = refundDate;
	}

	public String getRefundUser() {
		return refundUser;
	}

	public void setRefundUser(String refundUser) {
		this.refundUser = refundUser;
	}

	

}