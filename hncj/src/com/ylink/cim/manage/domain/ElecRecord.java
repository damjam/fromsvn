package com.ylink.cim.manage.domain;

import java.util.Date;
// default package


/**
 * ElecRecord entity. @author MyEclipse Persistence Tools
 */

public class ElecRecord implements java.io.Serializable {

	// Fields

	private String id;
	private String houseSn;
	private Integer prenum;
	private Integer curnum;
	private Integer num;
	private String preRecordDate;
	private String curRecordDate;
	private String createUser;
	private Date createDate;
	private String state;
	private Date checkDate;
	private String checkUser;
	private String recordMonth;
	private String remark;
	private String branchNo;
	
	public String getBranchNo() {
		return branchNo;
	}



	public void setBranchNo(String branchNo) {
		this.branchNo = branchNo;
	}
	// Constructors

	/** default constructor */
	public ElecRecord() {
	}

	/** full constructor */
	public ElecRecord(String id, String houseSn, Integer prenum, Integer curnum, Integer num, String preRecordDate,
			String curRecordDate, String createUser, Date createDate, String state, Date checkDate,
			String checkUser, String recordMonth, String remark) {
		this.id = id;
		this.houseSn = houseSn;
		this.prenum = prenum;
		this.curnum = curnum;
		this.num = num;
		this.preRecordDate = preRecordDate;
		this.curRecordDate = curRecordDate;
		this.createUser = createUser;
		this.createDate = createDate;
		this.state = state;
		this.checkDate = checkDate;
		this.checkUser = checkUser;
		this.recordMonth = recordMonth;
		this.remark = remark;
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

	public Integer getPrenum() {
		return this.prenum;
	}

	public void setPrenum(Integer prenum) {
		this.prenum = prenum;
	}

	public Integer getCurnum() {
		return this.curnum;
	}

	public void setCurnum(Integer curnum) {
		this.curnum = curnum;
	}

	public Integer getNum() {
		return this.num;
	}

	public void setNum(Integer num) {
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
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Date getCheckDate() {
		return this.checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}

	public String getCheckUser() {
		return this.checkUser;
	}

	public void setCheckUser(String checkUser) {
		this.checkUser = checkUser;
	}

	public String getRecordMonth() {
		return this.recordMonth;
	}

	public void setRecordMonth(String recordMonth) {
		this.recordMonth = recordMonth;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}