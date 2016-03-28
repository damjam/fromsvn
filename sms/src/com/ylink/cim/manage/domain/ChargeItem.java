package com.ylink.cim.manage.domain;


import java.util.Date;

/**
 * ChargeItem entity. @author MyEclipse Persistence Tools
 */

public class ChargeItem extends BranchField implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields

	private String id;
	private String way;
	private String item;
	private Double unitPrice;
	private Double basePrice;
	private Integer baseFloor;
	private Double stepPrice;
	private Double capPrice;
	private String segRule;
	private Date createDate;
	private Date updateDate;
	private String updateUser;
	private String remark;
	private String ruleDesc;
	private String itemName;
	
	
	
	// Constructors

	
	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getRuleDesc() {
		return ruleDesc;
	}

	public Integer getBaseFloor() {
		return baseFloor;
	}

	public void setBaseFloor(Integer baseFloor) {
		this.baseFloor = baseFloor;
	}

	public void setRuleDesc(String ruleDesc) {
		this.ruleDesc = ruleDesc;
	}

	public String getSegRule() {
		return segRule;
	}

	public void setSegRule(String segRule) {
		this.segRule = segRule;
	}

	/** default constructor */
	public ChargeItem() {
	}

	/** minimal constructor */
	public ChargeItem(String id) {
		this.id = id;
	}

	/** full constructor */


	// Property accessors

	public String getId() {
		return this.id;
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
		return this.item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public Double getUnitPrice() {
		return this.unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public Double getBasePrice() {
		return this.basePrice;
	}

	public void setBasePrice(Double basePrice) {
		this.basePrice = basePrice;
	}

	public Double getStepPrice() {
		return this.stepPrice;
	}

	public void setStepPrice(Double stepPrice) {
		this.stepPrice = stepPrice;
	}

	public Double getCapPrice() {
		return this.capPrice;
	}

	public void setCapPrice(Double capPrice) {
		this.capPrice = capPrice;
	}


	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdateUser() {
		return this.updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}