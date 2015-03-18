package com.ylink.cim.manage.view;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class WaterRecordActionForm extends ActionForm{

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
	
	
	public String getCreateUser() {
		return createUser;
	}
	public String getCurnum() {
		return curnum;
	}
	
	public String getCurRecordDate() {
		return curRecordDate;
	}
	public String getEndCreateDate() {
		return endCreateDate;
	}
	public String getEndRecordDate() {
		return endRecordDate;
	}
	public String getEndRecordMonth() {
		return endRecordMonth;
	}
	public FormFile getFile() {
		return File;
	}
	public String getHouseSn() {
		return houseSn;
	}
	public String getId() {
		return id;
	}
	public String getNum() {
		return num;
	}
	public String getPrenum() {
		return prenum;
	}
	public String getPreRecordDate() {
		return preRecordDate;
	}
	public String getRecordMonth() {
		return recordMonth;
	}
	public String getRemark() {
		return remark;
	}
	public String getStartCreateDate() {
		return startCreateDate;
	}
	public String getStartRecordDate() {
		return startRecordDate;
	}
	public String getStartRecordMonth() {
		return startRecordMonth;
	}
	public String getState() {
		return state;
	}
	public String getYear() {
		return year;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public void setCurnum(String curnum) {
		this.curnum = curnum;
	}
	public void setCurRecordDate(String curRecordDate) {
		this.curRecordDate = curRecordDate;
	}
	public void setEndCreateDate(String endCreateDate) {
		this.endCreateDate = endCreateDate;
	}
	public void setEndRecordDate(String endRecordDate) {
		this.endRecordDate = endRecordDate;
	}
	public void setEndRecordMonth(String endRecordMonth) {
		this.endRecordMonth = endRecordMonth;
	}
	public void setFile(FormFile file) {
		File = file;
	}
	public void setHouseSn(String houseSn) {
		this.houseSn = houseSn;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public void setPrenum(String prenum) {
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
	public void setStartRecordDate(String startRecordDate) {
		this.startRecordDate = startRecordDate;
	}
	public void setStartRecordMonth(String startRecordMonth) {
		this.startRecordMonth = startRecordMonth;
	}
	public void setState(String state) {
		this.state = state;
	}
	public void setYear(String year) {
		this.year = year;
	}
	
}
