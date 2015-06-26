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
	
	
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	
	public String getCsBillId() {
		return csBillId;
	}
	public void setCsBillId(String csBillId) {
		this.csBillId = csBillId;
	}
	public Double getSupFee() {
		return supFee;
	}
	public void setSupFee(Double supFee) {
		this.supFee = supFee;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	
	public String getFloor() {
		return floor;
	}
	public void setFloor(String floor) {
		this.floor = floor;
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
	public Double getArea() {
		return area;
	}
	public void setArea(Double area) {
		this.area = area;
	}
	public Double getCleanPrice() {
		return cleanPrice;
	}
	public void setCleanPrice(Double cleanPrice) {
		this.cleanPrice = cleanPrice;
	}
	public Double getCleanAmount() {
		return cleanAmount;
	}
	public void setCleanAmount(Double cleanAmount) {
		this.cleanAmount = cleanAmount;
	}
	public Double getLiftFee() {
		return liftFee;
	}
	public void setLiftFee(Double liftFee) {
		this.liftFee = liftFee;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
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
