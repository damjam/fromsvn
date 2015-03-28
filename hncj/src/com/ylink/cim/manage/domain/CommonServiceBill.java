package com.ylink.cim.manage.domain;
// default package

import java.util.Date;

/**
 * CommomServiceBillId domain. @author MyEclipse Persistence Tools
 */

public class CommonServiceBill implements java.io.Serializable {

	// Fields

	private String id;
	private String houseSn;
	private String ownerId;
	private String ownerName;
	private Double area;
	private Double servicePrice;
	private Double serviceAmount;
	private String startDate;
	private String endDate;
	private Double lightPrice;
	private Double lightAmount;
	private Integer monthNum;
	private Double totalAmount;
	private Date createDate;
	private String createUser;
	private String state;
	private Date chargeDate;
	private String chargeUser;
	private String remark;
	private String branchNo;
	
	public String getBranchNo() {
		return branchNo;
	}



	public void setBranchNo(String branchNo) {
		this.branchNo = branchNo;
	}
	// Property accessors

	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getChargeDate() {
		return chargeDate;
	}

	public String getChargeUser() {
		return chargeUser;
	}

	public void setChargeUser(String chargeUser) {
		this.chargeUser = chargeUser;
	}

	public void setChargeDate(Date chargeDate) {
		this.chargeDate = chargeDate;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

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

	public String getOwnerId() {
		return this.ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public String getOwnerName() {
		return this.ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public Double getArea() {
		return this.area;
	}

	public void setArea(Double area) {
		this.area = area;
	}

	public Double getServicePrice() {
		return this.servicePrice;
	}

	public void setServicePrice(Double servicePrice) {
		this.servicePrice = servicePrice;
	}

	public Double getServiceAmount() {
		return this.serviceAmount;
	}

	public void setServiceAmount(Double serviceAmount) {
		this.serviceAmount = serviceAmount;
	}

	public String getStartDate() {
		return this.startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return this.endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public Double getLightPrice() {
		return this.lightPrice;
	}

	public void setLightPrice(Double lightPrice) {
		this.lightPrice = lightPrice;
	}

	public Double getLightAmount() {
		return this.lightAmount;
	}

	public void setLightAmount(Double lightAmount) {
		this.lightAmount = lightAmount;
	}

	public Integer getMonthNum() {
		return this.monthNum;
	}

	public void setMonthNum(Integer monthNum) {
		this.monthNum = monthNum;
	}

	public Double getTotalAmount() {
		return this.totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	

}