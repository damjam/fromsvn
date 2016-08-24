package com.ylink.cim.manage.domain;


import java.util.Date;

/**
 * CarInfoId domain. @author MyEclipse Persistence Tools
 */

public class CarInfo extends BranchField implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields

	private String carSn;
	private String brand;
	private String model;
	private String ownerId;
	private String ownerName;
	private String parkingSn;
	private Date createDate;
	private String createUser;
	private Date updateDate;
	private String updateUser;
	private String remark;
	private String houseSn;
	private String ownerCel;
	private String snCity;
	private String id;

	/** default constructor */
	public CarInfo() {
	}

	/** minimal constructor */
	public CarInfo(String carSn) {
		this.carSn = carSn;
	}


	public String getBrand() {
		return this.brand;
	}

	public String getCarSn() {
		return this.carSn;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public String getCreateUser() {
		return this.createUser;
	}

	public String getHouseSn() {
		return houseSn;
	}

	public String getId() {
		return id;
	}

	public String getModel() {
		return this.model;
	}

	public String getOwnerCel() {
		return ownerCel;
	}

	public String getOwnerId() {
		return this.ownerId;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public String getParkingSn() {
		return this.parkingSn;
	}

	public String getRemark() {
		return remark;
	}

	// Property accessors

	public String getSnCity() {
		return snCity;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public String getUpdateUser() {
		return this.updateUser;
	}

	public void setBrand(String brand) {
		this.brand = brand;
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

	public void setHouseSn(String houseSn) {
		this.houseSn = houseSn;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public void setOwnerCel(String ownerCel) {
		this.ownerCel = ownerCel;
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

	public void setSnCity(String snCity) {
		this.snCity = snCity;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

}