package com.ylink.cim.manage.domain;

import java.io.File;
import java.util.Date;

/**
 * ElecRecord domain. @author MyEclipse Persistence Tools
 */

public class ElecRecord extends BranchField implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
	private String startCreateDate;
	private String endCreateDate;
	private String fileName;
	private File file;
	
	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	/** default constructor */
	public ElecRecord() {
	}

	public Date getCheckDate() {
		return this.checkDate;
	}

	public String getCheckUser() {
		return this.checkUser;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public String getCreateUser() {
		return this.createUser;
	}

	public Integer getCurnum() {
		return this.curnum;
	}

	// Constructors

	public String getCurRecordDate() {
		return this.curRecordDate;
	}

	public String getEndCreateDate() {
		return endCreateDate;
	}

	// Property accessors

	public String getFileName() {
		return fileName;
	}

	public String getHouseSn() {
		return this.houseSn;
	}

	public String getId() {
		return this.id;
	}

	public Integer getNum() {
		return this.num;
	}

	public Integer getPrenum() {
		return this.prenum;
	}

	public String getPreRecordDate() {
		return this.preRecordDate;
	}

	public String getRecordMonth() {
		return this.recordMonth;
	}

	public String getRemark() {
		return this.remark;
	}

	public String getStartCreateDate() {
		return startCreateDate;
	}

	public String getState() {
		return this.state;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}

	public void setCheckUser(String checkUser) {
		this.checkUser = checkUser;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public void setCurnum(Integer curnum) {
		this.curnum = curnum;
	}

	public void setCurRecordDate(String curRecordDate) {
		this.curRecordDate = curRecordDate;
	}

	public void setEndCreateDate(String endCreateDate) {
		this.endCreateDate = endCreateDate;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setHouseSn(String houseSn) {
		this.houseSn = houseSn;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public void setPrenum(Integer prenum) {
		this.prenum = prenum;
	}

	public void setPreRecordDate(String preRecordDate) {
		this.preRecordDate = preRecordDate;
	}

	public void setRecordMonth(String recordMonth) {
		this.recordMonth = recordMonth;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setStartCreateDate(String startCreateDate) {
		this.startCreateDate = startCreateDate;
	}

	public void setState(String state) {
		this.state = state;
	}

}