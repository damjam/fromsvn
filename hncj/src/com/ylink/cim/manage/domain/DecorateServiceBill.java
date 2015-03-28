package com.ylink.cim.manage.domain;
// default package

import java.util.Date;

/**
 * DecorateServiceBillId domain. @author MyEclipse Persistence Tools
 */

public class DecorateServiceBill implements java.io.Serializable {

	// Fields

	private String id;
	private String houseSn;
	private Double area;
	private Double cleanPrice;
	private Double cleanAmount;
	private Double liftFee;
	private Double amount;
	private Date createDate;
	private String chargeUser;
	private Date chargeDate;
	private String createUser;
	private String state;
	private String remark;
	private String ownerName;
	private Double supFee;
	private String csBillId;
	private String branchNo;
	
	public String getBranchNo() {
		return branchNo;
	}



	public void setBranchNo(String branchNo) {
		this.branchNo = branchNo;
	}
	
	// Constructors
	
	
	public String getCsBillId() {
		return csBillId;
	}

	public void setCsBillId(String csBillId) {
		this.csBillId = csBillId;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public Double getSupFee() {
		return supFee;
	}

	public void setSupFee(Double supFee) {
		this.supFee = supFee;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getState() {
		return state;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public void setState(String state) {
		this.state = state;
	}

	/** default constructor */
	public DecorateServiceBill() {
	}

	/** minimal constructor */
	public DecorateServiceBill(String id) {
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

	public Double getArea() {
		return this.area;
	}

	public void setArea(Double area) {
		this.area = area;
	}

	public Double getCleanPrice() {
		return this.cleanPrice;
	}

	public void setCleanPrice(Double cleanPrice) {
		this.cleanPrice = cleanPrice;
	}

	public Double getCleanAmount() {
		return this.cleanAmount;
	}

	public void setCleanAmount(Double cleanAmount) {
		this.cleanAmount = cleanAmount;
	}

	public Double getLiftFee() {
		return this.liftFee;
	}

	public void setLiftFee(Double liftFee) {
		this.liftFee = liftFee;
	}

	public Double getAmount() {
		return this.amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
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

	

}