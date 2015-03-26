package com.ylink.cim.manage.domain;
// default package

import java.util.Date;

/**
 * ParkingInfo entity. @author MyEclipse Persistence Tools
 */

public class ParkingInfo implements java.io.Serializable {

	// Fields

	private String id;
	private String sn;
	private String state;
	private String carSn;
	private Date createDate;
	private String createUser;
	private String remark;
	private Date updateDate;
	private String updateUser;
	private String ownerName;
	private String ownerCel;
	private String endUser;
	private String endUserCel;
	private String branchNo;
	private String houseSn;
	
	
	
	// Constructors

	
	
	
	public String getHouseSn() {
		return houseSn;
	}

	public String getEndUser() {
		return endUser;
	}

	public void setEndUser(String endUser) {
		this.endUser = endUser;
	}

	public String getEndUserCel() {
		return endUserCel;
	}

	public void setEndUserCel(String endUserCel) {
		this.endUserCel = endUserCel;
	}

	public void setHouseSn(String houseSn) {
		this.houseSn = houseSn;
	}

	public String getBranchNo() {
		return branchNo;
	}

	public void setBranchNo(String branchNo) {
		this.branchNo = branchNo;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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

	/** default constructor */
	public ParkingInfo() {
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSn() {
		return this.sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCarSn() {
		return this.carSn;
	}

	public void setCarSn(String carSn) {
		this.carSn = carSn;
	}

	

	public String getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

}