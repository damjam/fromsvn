package com.ylink.cim.manage.view;

import java.util.Date;

import org.apache.struts.action.ActionForm;

public class DecorateServiceBillActionForm extends ActionForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String houseSn;
	private Double area;
	private Double cleanPrice;
	private Double cleanAmount;
	private Double liftFee;
	private Double amount;
	private Date createDate;
	private String createUser;
	private String ownerName;
	private String state;
	private String floor;
	private String remark;
	private String startChargeDate;
	private String endChargeDate;
	private Double supFee;
	private String csBillId;
	private String year;
	
	
	public Double getAmount() {
		return amount;
	}
	public Double getArea() {
		return area;
	}
	
	public Double getCleanAmount() {
		return cleanAmount;
	}
	public Double getCleanPrice() {
		return cleanPrice;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public String getCreateUser() {
		return createUser;
	}
	public String getCsBillId() {
		return csBillId;
	}
	public String getEndChargeDate() {
		return endChargeDate;
	}
	public String getFloor() {
		return floor;
	}
	public String getHouseSn() {
		return houseSn;
	}
	public String getId() {
		return id;
	}
	public Double getLiftFee() {
		return liftFee;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public String getRemark() {
		return remark;
	}
	
	public String getStartChargeDate() {
		return startChargeDate;
	}
	public String getState() {
		return state;
	}
	public Double getSupFee() {
		return supFee;
	}
	public String getYear() {
		return year;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public void setArea(Double area) {
		this.area = area;
	}
	public void setCleanAmount(Double cleanAmount) {
		this.cleanAmount = cleanAmount;
	}
	public void setCleanPrice(Double cleanPrice) {
		this.cleanPrice = cleanPrice;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public void setCsBillId(String csBillId) {
		this.csBillId = csBillId;
	}
	public void setEndChargeDate(String endChargeDate) {
		this.endChargeDate = endChargeDate;
	}
	public void setFloor(String floor) {
		this.floor = floor;
	}
	public void setHouseSn(String houseSn) {
		this.houseSn = houseSn;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setLiftFee(Double liftFee) {
		this.liftFee = liftFee;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public void setStartChargeDate(String startChargeDate) {
		this.startChargeDate = startChargeDate;
	}
	public void setState(String state) {
		this.state = state;
	}
	public void setSupFee(Double supFee) {
		this.supFee = supFee;
	}
	public void setYear(String year) {
		this.year = year;
	}
	
}
