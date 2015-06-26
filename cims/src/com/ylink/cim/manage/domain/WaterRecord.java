package com.ylink.cim.manage.domain;
// default package

import java.util.Date;

/**
 * WaterRecordId entity. @author MyEclipse Persistence Tools
 */

public class WaterRecord implements java.io.Serializable {

	// Fields

	private String id;
	private String houseSn;
	private Long prenum;
	private Long curnum;
	private Long num;
	private String preRecordDate;
	private String curRecordDate;
	private String createUser;
	private Date createDate;
	private String state;
	private Date checkDate;
	private String checkUser;
	private String recordMonth;
	private String remark;
	// Constructors
	
	
	/** default constructor */
	public WaterRecord() {
	}

	

	public String getRemark() {
		return remark;
	}



	public void setRemark(String remark) {
		this.remark = remark;
	}



	public String getRecordMonth() {
		return recordMonth;
	}



	public void setRecordMonth(String recordMonth) {
		this.recordMonth = recordMonth;
	}



	/** minimal constructor */
	public WaterRecord(String id) {
		this.id = id;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHouseSn() {
		return this.houseSn;
	}

	public void setHouseSn(String houseSn) {
		this.houseSn = houseSn;
	}
	

	public Long getPrenum() {
		return prenum;
	}

	public void setPrenum(Long prenum) {
		this.prenum = prenum;
	}

	public Long getCurnum() {
		return curnum;
	}

	public void setCurnum(Long curnum) {
		this.curnum = curnum;
	}

	public Long getNum() {
		return num;
	}

	public void setNum(Long num) {
		this.num = num;
	}

	public String getPreRecordDate() {
		return this.preRecordDate;
	}

	public void setPreRecordDate(String preRecordDate) {
		this.preRecordDate = preRecordDate;
	}

	public String getCurRecordDate() {
		return this.curRecordDate;
	}

	public void setCurRecordDate(String curRecordDate) {
		this.curRecordDate = curRecordDate;
	}

	public String getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Date getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}

	public String getCheckUser() {
		return checkUser;
	}

	public void setCheckUser(String checkUser) {
		this.checkUser = checkUser;
	}
	
}