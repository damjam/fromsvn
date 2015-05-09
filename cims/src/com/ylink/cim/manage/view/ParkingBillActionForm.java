package com.ylink.cim.manage.view;

import java.util.Date;

import org.apache.struts.action.ActionForm;

public class ParkingBillActionForm extends ActionForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String ownerId;
	private Date createDate;
	private String createUser;
	private Double amount;
	private String beginDate;
	private String endDate;
	private String parkingSn;
	private String carSn;
	private String houseSn;
	private String remark;
	private String isInternal;
	private String ownerName;
	private Integer monthNum;
	private String state;
	private String year;
	private String orderType;
	private String mobile;
	
	// Constructors
	
	
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Integer getMonthNum() {
		return monthNum;
	}
	public void setMonthNum(Integer monthNum) {
		this.monthNum = monthNum;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public String getIsInternal() {
		return isInternal;
	}
	public void setIsInternal(String isInternal) {
		this.isInternal = isInternal;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getHouseSn() {
		return houseSn;
	}
	public void setHouseSn(String houseSn) {
		this.houseSn = houseSn;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
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
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getParkingSn() {
		return parkingSn;
	}
	public void setParkingSn(String parkingSn) {
		this.parkingSn = parkingSn;
	}
	public String getCarSn() {
		return carSn;
	}
	public void setCarSn(String carSn) {
		this.carSn = carSn;
	}
	
	
}
