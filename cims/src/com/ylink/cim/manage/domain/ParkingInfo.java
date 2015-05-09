package com.ylink.cim.manage.domain;
// default package

import java.sql.Timestamp;
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
	private Timestamp craeteDate;
	private String createUser;
	private String remark;
	private Date updateDate;
	private String updateUser;
	// Constructors

	/** default constructor */
	public ParkingInfo() {
	}

	/** minimal constructor */
	public ParkingInfo(String id, String sn, String state, String carSn, Timestamp craeteDate, String createUser) {
		this.id = id;
		this.sn = sn;
		this.state = state;
		this.carSn = carSn;
		this.craeteDate = craeteDate;
		this.createUser = createUser;
	}

	/** full constructor */
	public ParkingInfo(String id, String sn, String state, String carSn, Timestamp craeteDate, String createUser,
			String remark, Date updateDate, String updateUser) {
		this.id = id;
		this.sn = sn;
		this.state = state;
		this.carSn = carSn;
		this.craeteDate = craeteDate;
		this.createUser = createUser;
		this.remark = remark;
		this.updateDate = updateDate;
		this.updateUser = updateUser;
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

	public Timestamp getCraeteDate() {
		return this.craeteDate;
	}

	public void setCraeteDate(Timestamp craeteDate) {
		this.craeteDate = craeteDate;
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