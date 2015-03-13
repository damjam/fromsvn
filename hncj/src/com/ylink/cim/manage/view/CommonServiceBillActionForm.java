package com.ylink.cim.manage.view;

import java.util.Date;

import org.apache.struts.action.ActionForm;

public class CommonServiceBillActionForm extends ActionForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String houseSn;
	private String ownerId;
	private String ownerName;
	private Double area;
	private String servicePrice;
	private String serviceAmount;
	private String startDate;
	private String endDate;
	private Double lightPrice;
	private Double lightAmount;
	private Integer monthNum;
	private Double totalAmount;
	private Date createDate;
	private String createUser;
	private String state;
	private String startChargeDate;
	private String endChargeDate;
	private String remark;
	private String checkinState;
	private String suPayState;
	private String year;
	
	
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	
	public String getSuPayState() {
		return suPayState;
	}
	public void setSuPayState(String suPayState) {
		this.suPayState = suPayState;
	}
	public String getCheckinState() {
		return checkinState;
	}
	public void setCheckinState(String checkinState) {
		this.checkinState = checkinState;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
	public String getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public Double getArea() {
		return area;
	}
	public void setArea(Double area) {
		this.area = area;
	}
	public String getServicePrice() {
		return servicePrice;
	}
	public void setServicePrice(String servicePrice) {
		this.servicePrice = servicePrice;
	}
	public String getServiceAmount() {
		return serviceAmount;
	}
	public void setServiceAmount(String serviceAmount) {
		this.serviceAmount = serviceAmount;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public Double getLightPrice() {
		return lightPrice;
	}
	public void setLightPrice(Double lightPrice) {
		this.lightPrice = lightPrice;
	}
	public Double getLightAmount() {
		return lightAmount;
	}
	public void setLightAmount(Double lightAmount) {
		this.lightAmount = lightAmount;
	}
	public Integer getMonthNum() {
		return monthNum;
	}
	public void setMonthNum(Integer monthNum) {
		this.monthNum = monthNum;
	}
	public Double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	
}
