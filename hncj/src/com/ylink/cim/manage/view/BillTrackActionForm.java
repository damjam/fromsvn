package com.ylink.cim.manage.view;

import java.util.Date;

import org.apache.struts.action.ActionForm;

public class BillTrackActionForm extends ActionForm {

	private String id;
	private String billType;
	private String expireDate;
	private String ownerName;
	private String ownerCel;
	private Integer noticeTimes;
	private Date createDate;
	private String endUser;
	private String state;
	private String endUserCel;
	private Integer leftDays;
	private Integer overDays;
	private String houseSn;
	private String billId;
	private String branchNo;
	
	
	
	public String getBranchNo() {
		return branchNo;
	}
	public void setBranchNo(String branchNo) {
		this.branchNo = branchNo;
	}
	public String getBillId() {
		return billId;
	}
	public void setBillId(String billId) {
		this.billId = billId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBillType() {
		return billType;
	}
	public void setBillType(String billType) {
		this.billType = billType;
	}
	public String getExpireDate() {
		return expireDate;
	}
	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
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
	public Integer getNoticeTimes() {
		return noticeTimes;
	}
	public void setNoticeTimes(Integer noticeTimes) {
		this.noticeTimes = noticeTimes;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getEndUser() {
		return endUser;
	}
	public void setEndUser(String endUser) {
		this.endUser = endUser;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getEndUserCel() {
		return endUserCel;
	}
	public void setEndUserCel(String endUserCel) {
		this.endUserCel = endUserCel;
	}
	public Integer getLeftDays() {
		return leftDays;
	}
	public void setLeftDays(Integer leftDays) {
		this.leftDays = leftDays;
	}
	public Integer getOverDays() {
		return overDays;
	}
	public void setOverDays(Integer overDays) {
		this.overDays = overDays;
	}
	public String getHouseSn() {
		return houseSn;
	}
	public void setHouseSn(String houseSn) {
		this.houseSn = houseSn;
	}
	
	
}
