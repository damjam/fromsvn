package com.ylink.cim.manage.domain;
// default package

import java.util.Date;

/**
 * CarInfoId entity. @author MyEclipse Persistence Tools
 */

public class CarInfo implements java.io.Serializable {

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
	private String branchNo;
	private String remark;
	private String houseSn;
	private String ownerCel;
	private String snCity;
	
	
	
	public String getOwnerName() {
		return ownerName;
	}



	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}



	public String getSnCity() {
		return snCity;
	}



	public void setSnCity(String snCity) {
		this.snCity = snCity;
	}



	public String getOwnerCel() {
		return ownerCel;
	}



	public void setOwnerCel(String ownerCel) {
		this.ownerCel = ownerCel;
	}



	public String getHouseSn() {
		return houseSn;
	}



	public void setHouseSn(String houseSn) {
		this.houseSn = houseSn;
	}



	public String getRemark() {
		return remark;
	}



	public void setRemark(String remark) {
		this.remark = remark;
	}



	public String getBranchNo() {
		return branchNo;
	}



	public void setBranchNo(String branchNo) {
		this.branchNo = branchNo;
	}
	// Constructors

	/** default constructor */
	public CarInfo() {
	}

	/** minimal constructor */
	public CarInfo(String carSn) {
		this.carSn = carSn;
	}


	// Property accessors

	public String getCarSn() {
		return this.carSn;
	}

	public void setCarSn(String carSn) {
		this.carSn = carSn;
	}

	public String getBrand() {
		return this.brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getModel() {
		return this.model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getOwnerId() {
		return this.ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public String getParkingSn() {
		return this.parkingSn;
	}

	public void setParkingSn(String parkingSn) {
		this.parkingSn = parkingSn;
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

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdateUser() {
		return this.updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof CarInfo))
			return false;
		CarInfo castOther = (CarInfo) other;

		return ((this.getCarSn() == castOther.getCarSn()) || (this.getCarSn() != null && castOther.getCarSn() != null && this
				.getCarSn().equals(castOther.getCarSn())))
				&& ((this.getBrand() == castOther.getBrand()) || (this.getBrand() != null
						&& castOther.getBrand() != null && this.getBrand().equals(castOther.getBrand())))
				&& ((this.getModel() == castOther.getModel()) || (this.getModel() != null
						&& castOther.getModel() != null && this.getModel().equals(castOther.getModel())))
				&& ((this.getOwnerId() == castOther.getOwnerId()) || (this.getOwnerId() != null
						&& castOther.getOwnerId() != null && this.getOwnerId().equals(castOther.getOwnerId())))
				&& ((this.getParkingSn() == castOther.getParkingSn()) || (this.getParkingSn() != null
						&& castOther.getParkingSn() != null && this.getParkingSn().equals(castOther.getParkingSn())))
				&& ((this.getCreateDate() == castOther.getCreateDate()) || (this.getCreateDate() != null
						&& castOther.getCreateDate() != null && this.getCreateDate().equals(castOther.getCreateDate())))
				&& ((this.getCreateUser() == castOther.getCreateUser()) || (this.getCreateUser() != null
						&& castOther.getCreateUser() != null && this.getCreateUser().equals(castOther.getCreateUser())))
				&& ((this.getUpdateDate() == castOther.getUpdateDate()) || (this.getUpdateDate() != null
						&& castOther.getUpdateDate() != null && this.getUpdateDate().equals(castOther.getUpdateDate())))
				&& ((this.getUpdateUser() == castOther.getUpdateUser()) || (this.getUpdateUser() != null
						&& castOther.getUpdateUser() != null && this.getUpdateUser().equals(castOther.getUpdateUser())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getCarSn() == null ? 0 : this.getCarSn().hashCode());
		result = 37 * result + (getBrand() == null ? 0 : this.getBrand().hashCode());
		result = 37 * result + (getModel() == null ? 0 : this.getModel().hashCode());
		result = 37 * result + (getOwnerId() == null ? 0 : this.getOwnerId().hashCode());
		result = 37 * result + (getParkingSn() == null ? 0 : this.getParkingSn().hashCode());
		result = 37 * result + (getCreateDate() == null ? 0 : this.getCreateDate().hashCode());
		result = 37 * result + (getCreateUser() == null ? 0 : this.getCreateUser().hashCode());
		result = 37 * result + (getUpdateDate() == null ? 0 : this.getUpdateDate().hashCode());
		result = 37 * result + (getUpdateUser() == null ? 0 : this.getUpdateUser().hashCode());
		return result;
	}

}