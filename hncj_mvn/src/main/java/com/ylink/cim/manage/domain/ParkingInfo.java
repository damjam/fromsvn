package com.ylink.cim.manage.domain;



import java.util.Date;

/**
 * ParkingInfo domain. @author MyEclipse Persistence Tools
 */

public class ParkingInfo extends BranchField implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
	private String houseSn;

	// Constructors

	/** default constructor */
	public ParkingInfo() {
	}


	public String getCarSn() {
		return this.carSn;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public String getCreateUser() {
		return this.createUser;
	}

	public String getEndUser() {
		return endUser;
	}


	public String getHouseSn() {
		return houseSn;
	}

	public String getId() {
		return this.id;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public String getRemark() {
		return this.remark;
	}

	public String getSn() {
		return this.sn;
	}

	public String getState() {
		return this.state;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	// Property accessors

	public String getUpdateUser() {
		return this.updateUser;
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

	public void setEndUser(String endUser) {
		this.endUser = endUser;
	}

	public void setHouseSn(String houseSn) {
		this.houseSn = houseSn;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}


	public String getOwnerCel() {
		return ownerCel;
	}


	public void setOwnerCel(String ownerCel) {
		this.ownerCel = ownerCel;
	}


	public String getEndUserCel() {
		return endUserCel;
	}


	public void setEndUserCel(String endUserCel) {
		this.endUserCel = endUserCel;
	}

	
}