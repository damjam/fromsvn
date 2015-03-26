package com.ylink.cim.manage.view;

import org.apache.struts.action.ActionForm;

public class ParkingInfoActionForm extends ActionForm{

	private String id;
	private String sn;
	private String state;
	private String carSn;
	private String createUser;
	private String remark;
	private String updateUser;
	private String ownerName;
	private String ownerCel;
	private String endUser;
	private String endUserCel;
	private String houseSn;
	
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
	public String getHouseSn() {
		return houseSn;
	}
	public void setHouseSn(String houseSn) {
		this.houseSn = houseSn;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCarSn() {
		return carSn;
	}
	public void setCarSn(String carSn) {
		this.carSn = carSn;
	}
	
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
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
	
	
}
