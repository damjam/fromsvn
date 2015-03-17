package com.ylink.cim.manage.view;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class ElecRecordActionForm extends ActionForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String houseSn;
	private String prenum;
	private String curnum;
	private String num;
	private String preRecordDate;
	private String curRecordDate;
	private String startRecordDate;
	private String endRecordDate;
	private String createUser;
	private String startCreateDate;
	private String endCreateDate;
	private String state;
	private String recordMonth;
	private String startRecordMonth;
	private String endRecordMonth;
	private String remark;
	private FormFile File;
	private String year;
	
	
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	
	public FormFile getFile() {
		return File;
	}
	public void setFile(FormFile file) {
		File = file;
	}
	public String getStartRecordDate() {
		return startRecordDate;
	}
	public void setStartRecordDate(String startRecordDate) {
		this.startRecordDate = startRecordDate;
	}
	public String getEndRecordDate() {
		return endRecordDate;
	}
	public void setEndRecordDate(String endRecordDate) {
		this.endRecordDate = endRecordDate;
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
	public String getStartRecordMonth() {
		return startRecordMonth;
	}
	public void setStartRecordMonth(String startRecordMonth) {
		this.startRecordMonth = startRecordMonth;
	}
	public String getEndRecordMonth() {
		return endRecordMonth;
	}
	public void setEndRecordMonth(String endRecordMonth) {
		this.endRecordMonth = endRecordMonth;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getHouseSn() {
		return houseSn;
	}
	public void setHouseSn(String houseSn) {
		this.houseSn = houseSn;
	}
	public String getPrenum() {
		return prenum;
	}
	public void setPrenum(String prenum) {
		this.prenum = prenum;
	}
	public String getCurnum() {
		return curnum;
	}
	public void setCurnum(String curnum) {
		this.curnum = curnum;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getPreRecordDate() {
		return preRecordDate;
	}
	public void setPreRecordDate(String preRecordDate) {
		this.preRecordDate = preRecordDate;
	}
	public String getCurRecordDate() {
		return curRecordDate;
	}
	public void setCurRecordDate(String curRecordDate) {
		this.curRecordDate = curRecordDate;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getStartCreateDate() {
		return startCreateDate;
	}
	public void setStartCreateDate(String startCreateDate) {
		this.startCreateDate = startCreateDate;
	}
	public String getEndCreateDate() {
		return endCreateDate;
	}
	public void setEndCreateDate(String endCreateDate) {
		this.endCreateDate = endCreateDate;
	}
	
}
