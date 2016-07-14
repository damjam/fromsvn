package com.ylink.cim.manage.domain;


import java.util.Date;

import org.apache.commons.lang.StringUtils;

/**
 * RentalHouse entity. @author MyEclipse Persistence Tools
 */

public class RentalHouse extends BranchField implements java.io.Serializable {

	// Fields
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String houseSn;
	private Date createDate;
	private String createUser;
	private String state;
	private Double area;
	private Integer rentFee;
	private String remark;
	private String currentRenter;
	private String renterType;
	private String expireDate;
	private String floor;
	private String ownerName;
	private String ownerTel;
	private String saleState;
	private String houseType;
	private String payRule;
	private String[] configArray;
	private String otherRule;
	private String roomConfig;
	private String decorateType;
	private String renterId;
	private String buildingNo;
	
	
	public String getBuildingNo() {
		return buildingNo;
	}


	public void setBuildingNo(String buildingNo) {
		this.buildingNo = buildingNo;
	}


	public String getRenterId() {
		return renterId;
	}


	public void setRenterId(String renterId) {
		this.renterId = renterId;
	}


	public String getDecorateType() {
		return decorateType;
	}


	public void setDecorateType(String decorateType) {
		this.decorateType = decorateType;
	}


	public String getRoomConfig() {
		return roomConfig;
	}


	public void setRoomConfig(String roomConfig) {
		this.roomConfig = roomConfig;
	}


	public String getOtherRule() {
		return otherRule;
	}


	public void setOtherRule(String otherRule) {
		this.otherRule = otherRule;
	}


	public String[] getConfigArray() {
		return configArray;
	}


	public void setConfigArray(String[] configArray) {
		this.configArray = configArray;
	}


	public String getPayRule() {
		return payRule;
	}


	public void setPayRule(String payRule) {
		this.payRule = payRule;
	}


	public String getHouseType() {
		return houseType;
	}


	public void setHouseType(String houseType) {
		this.houseType = houseType;
	}


	public String getSaleState() {
		return saleState;
	}


	public void setSaleState(String saleState) {
		this.saleState = saleState;
	}


	public String getOwnerName() {
		return ownerName;
	}


	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}


	public String getOwnerTel() {
		return ownerTel;
	}


	public void setOwnerTel(String ownerTel) {
		this.ownerTel = ownerTel;
	}


	public String getFloor() {
		return floor;
	}


	public void setFloor(String floor) {
		this.floor = floor;
	}


	/** default constructor */
	public RentalHouse() {
	}


	// Property accessors

	public Integer getRentFee() {
		return rentFee;
	}



	public void setRentFee(Integer rentFee) {
		this.rentFee = rentFee;
	}



	public String getHouseSn() {
		return this.houseSn;
	}

	public void setHouseSn(String houseSn) {
		this.houseSn = StringUtils.upperCase(houseSn);
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

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Double getArea() {
		return this.area;
	}

	public void setArea(Double area) {
		this.area = area;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCurrentRenter() {
		return this.currentRenter;
	}

	public void setCurrentRenter(String currentRenter) {
		this.currentRenter = currentRenter;
	}

	public String getRenterType() {
		return this.renterType;
	}

	public void setRenterType(String renterType) {
		this.renterType = renterType;
	}

	public String getExpireDate() {
		return this.expireDate;
	}

	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
	}

}