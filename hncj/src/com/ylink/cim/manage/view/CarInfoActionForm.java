package com.ylink.cim.manage.view;

import java.util.Date;

import org.apache.struts.action.ActionForm;

public class CarInfoActionForm extends ActionForm{

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
	private String id;
	
	
	
	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public String getOwnerName() {
		return ownerName;
	}



	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
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

	public String getCarSn() {
		return carSn;
	}
	public void setCarSn(String carSn) {
		this.carSn = carSn;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
	public String getParkingSn() {
		return parkingSn;
	}
	public void setParkingSn(String parkingSn) {
		this.parkingSn = parkingSn;
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
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	public String getBranchNo() {
		return branchNo;
	}
	public void setBranchNo(String branchNo) {
		this.branchNo = branchNo;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
