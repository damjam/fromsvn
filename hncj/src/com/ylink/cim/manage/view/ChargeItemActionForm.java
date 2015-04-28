package com.ylink.cim.manage.view;

import java.util.Date;

import org.apache.struts.action.ActionForm;

public class ChargeItemActionForm extends ActionForm{

	private String id;
	private String way;
	private String item;
	private Double unitPrice;
	private Double basePrice;
	private Double stepPrice;
	private Double capPrice;
	private String segRule;
	private String ruleDesc;
	private Date createDate;
	private Date updateDate;
	private String updateUser;
	private String branchNo;
	private String remark;
	private Integer baseFloor;
	private String itemName;
	
	
	
	// Constructors

	
	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	
	
	public Integer getBaseFloor() {
		return baseFloor;
	}
	public void setBaseFloor(Integer baseFloor) {
		this.baseFloor = baseFloor;
	}
	public String getSegRule() {
		return segRule;
	}
	public void setSegRule(String segRule) {
		this.segRule = segRule;
	}
	public String getRuleDesc() {
		return ruleDesc;
	}
	public void setRuleDesc(String ruleDesc) {
		this.ruleDesc = ruleDesc;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getWay() {
		return way;
	}
	public void setWay(String way) {
		this.way = way;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public Double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}
	public Double getBasePrice() {
		return basePrice;
	}
	public void setBasePrice(Double basePrice) {
		this.basePrice = basePrice;
	}
	public Double getStepPrice() {
		return stepPrice;
	}
	public void setStepPrice(Double stepPrice) {
		this.stepPrice = stepPrice;
	}
	public Double getCapPrice() {
		return capPrice;
	}
	public void setCapPrice(Double capPrice) {
		this.capPrice = capPrice;
	}
	
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	public String getBranchNo() {
		return branchNo;
	}
	public void setBranchNo(String branchNo) {
		this.branchNo = branchNo;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
