package com.ylink.cim.manage.domain;

import java.util.Date;

/**
 * HouseInfo domain. @author MyEclipse Persistence Tools
 */

public class HouseInfo implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String houseSn;
	private Double area;
	private String buildingNo;
	private String unitNo;
	private String floor;
	private String position;
	private String deliveryDate;
	private String state;
	private String createUser;
	private Date createDate;
	private String houseDesc;
	private String remark;
	private String branchNo;
	private String orderSn;
	private String decorateState;
	
	public String getDecorateState() {
		return decorateState;
	}

	public void setDecorateState(String decorateState) {
		this.decorateState = decorateState;
	}

	/** default constructor */
	public HouseInfo() {
	}

	public Double getArea() {
		return area;
	}

	public String getBranchNo() {
		return branchNo;
	}

	public String getBuildingNo() {
		return this.buildingNo;
	}

	public Date getCreateDate() {
		return createDate;
	}

	// Property accessors

	public String getCreateUser() {
		return createUser;
	}

	public String getDeliveryDate() {
		return this.deliveryDate;
	}

	public String getFloor() {
		return this.floor;
	}

	public String getHouseDesc() {
		return houseDesc;
	}

	public String getHouseSn() {
		return this.houseSn;
	}

	public String getOrderSn() {
		return orderSn;
	}

	public String getPosition() {
		return position;
	}

	public String getRemark() {
		return remark;
	}

	public String getState() {
		return this.state;
	}

	public String getUnitNo() {
		return this.unitNo;
	}

	public void setArea(Double area) {
		this.area = area;
	}

	public void setBranchNo(String branchNo) {
		this.branchNo = branchNo;
	}

	// Constructors

	public void setBuildingNo(String buildingNo) {
		this.buildingNo = buildingNo;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public void setHouseDesc(String houseDesc) {
		this.houseDesc = houseDesc;
	}

	public void setHouseSn(String houseSn) {
		this.houseSn = houseSn;
	}

	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setUnitNo(String unitNo) {
		this.unitNo = unitNo;
	}

}