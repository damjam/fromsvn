package com.ylink.cim.manage.domain;

import java.util.Date;

/**
 * HouseInfoId entity. @author MyEclipse Persistence Tools
 */

public class HouseInfo implements java.io.Serializable {

	// Fields

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
	
	public String getBranchNo() {
		return branchNo;
	}



	public void setBranchNo(String branchNo) {
		this.branchNo = branchNo;
	}
	// Constructors

	/** default constructor */
	public HouseInfo() {
	}


	// Property accessors

	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}



	public String getHouseSn() {
		return this.houseSn;
	}

	public void setHouseSn(String houseSn) {
		this.houseSn = houseSn;
	}


	public String getBuildingNo() {
		return this.buildingNo;
	}

	public void setBuildingNo(String buildingNo) {
		this.buildingNo = buildingNo;
	}

	public String getUnitNo() {
		return this.unitNo;
	}

	public void setUnitNo(String unitNo) {
		this.unitNo = unitNo;
	}

	public String getFloor() {
		return this.floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}
	
	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getDeliveryDate() {
		return this.deliveryDate;
	}

	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Double getArea() {
		return area;
	}

	public void setArea(Double area) {
		this.area = area;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getHouseDesc() {
		return houseDesc;
	}

	public void setHouseDesc(String houseDesc) {
		this.houseDesc = houseDesc;
	}
	

}