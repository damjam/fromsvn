package com.ylink.cim.manage.view;

import java.util.Date;

import org.apache.struts.action.ActionForm;

public class ParkingBillActionForm extends ActionForm {

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

	public Double getAmount() {
		return amount;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public String getCarSn() {
		return carSn;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public String getCreateUser() {
		return createUser;
	}

	public String getEndDate() {
		return endDate;
	}

	public String getHouseSn() {
		return houseSn;
	}

	public String getId() {
		return id;
	}

	public String getIsInternal() {
		return isInternal;
	}

	public Integer getMonthNum() {
		return monthNum;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public String getParkingSn() {
		return parkingSn;
	}

	public String getRemark() {
		return remark;
	}

	public String getState() {
		return state;
	}

	public String getYear() {
		return year;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public void setCarSn(String carSn) {
		this.carSn = carSn;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public void setHouseSn(String houseSn) {
		this.houseSn = houseSn;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setIsInternal(String isInternal) {
		this.isInternal = isInternal;
	}

	public void setMonthNum(Integer monthNum) {
		this.monthNum = monthNum;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public void setParkingSn(String parkingSn) {
		this.parkingSn = parkingSn;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setYear(String year) {
		this.year = year;
	}

}
