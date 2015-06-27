package com.ylink.cim.manage.domain;

// default package

import java.util.Date;

/**
 * BillTrack entity. @author MyEclipse Persistence Tools
 */

public class BillTrack implements java.io.Serializable {

	// Fields

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

	// Constructors

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

	public String getHouseSn() {
		return houseSn;
	}

	public void setHouseSn(String houseSn) {
		this.houseSn = houseSn;
	}

	public Integer getLeftDays() {

		return leftDays == null ? 0 : leftDays;
	}

	public void setLeftDays(Integer leftDays) {
		this.leftDays = leftDays;
	}

	public Integer getOverDays() {

		return overDays == null ? 0 : overDays;
	}

	public void setOverDays(Integer overDays) {
		this.overDays = overDays;
	}

	/** default constructor */
	public BillTrack() {
	}

	/** minimal constructor */
	public BillTrack(String id) {
		this.id = id;
	}

	/** full constructor */
	public BillTrack(String id, String billType, String expireDate,
			String ownerName, String ownerCel, Integer noticeTimes,
			Date createDate, String endUser, String state, String endUserCel) {
		this.id = id;
		this.billType = billType;
		this.expireDate = expireDate;
		this.ownerName = ownerName;
		this.ownerCel = ownerCel;
		this.noticeTimes = noticeTimes;
		this.createDate = createDate;
		this.endUser = endUser;
		this.state = state;
		this.endUserCel = endUserCel;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBillType() {
		return this.billType;
	}

	public void setBillType(String billType) {
		this.billType = billType;
	}

	public String getExpireDate() {
		return this.expireDate;
	}

	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
	}

	public String getOwnerName() {
		return this.ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getOwnerCel() {
		return this.ownerCel;
	}

	public void setOwnerCel(String ownerCel) {
		this.ownerCel = ownerCel;
	}

	public Integer getNoticeTimes() {
		return this.noticeTimes == null ? 0 : noticeTimes;
	}

	public void setNoticeTimes(Integer noticeTimes) {
		this.noticeTimes = noticeTimes;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getEndUser() {
		return this.endUser;
	}

	public void setEndUser(String endUser) {
		this.endUser = endUser;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getEndUserCel() {
		return this.endUserCel;
	}

	public void setEndUserCel(String endUserCel) {
		this.endUserCel = endUserCel;
	}

}