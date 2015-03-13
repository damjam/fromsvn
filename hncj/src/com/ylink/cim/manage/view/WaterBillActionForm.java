package com.ylink.cim.manage.view;

import org.apache.struts.action.ActionForm;

public class WaterBillActionForm extends ActionForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String houseSn;
	private Long prenum;
	private Long curnum;
	private Long num;
	private Double amount;
	private String state;
	private String preRecordDate;
	private String curRecordDate;
	private String createUser;
	private String startCreateDate;
	private String endCreateDate;
	private String chargeUser;
	private String startChargeDate;
	private String endChargeDate;
	private String recordMonth;
	private String startRecordMonth;
	private String endRecordMonth;
	private String remark;
	private String buildingNo;
	private String year;
	
	
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getBuildingNo() {
		return buildingNo;
	}
	public void setBuildingNo(String buildingNo) {
		this.buildingNo = buildingNo;
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
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
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
	public String getChargeUser() {
		return chargeUser;
	}
	public void setChargeUser(String chargeUser) {
		this.chargeUser = chargeUser;
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
	public String getStartChargeDate() {
		return startChargeDate;
	}
	public void setStartChargeDate(String startChargeDate) {
		this.startChargeDate = startChargeDate;
	}
	public String getEndChargeDate() {
		return endChargeDate;
	}
	public void setEndChargeDate(String endChargeDate) {
		this.endChargeDate = endChargeDate;
	}
	
}
