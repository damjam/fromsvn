package com.ylink.cim.manage.domain;


import java.util.Date;

/**
 * ChargeParam entity. @author MyEclipse Persistence Tools
 */

public class ChargeParam extends BranchField implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields

	private String id;
	private String remark;
	private String rangeCode;
	private String chargeObj;
	private String paramName;
	private Date createDate;
	private String createUser;
	private String chargeDesc;
	private String[] itemIds;

	// Constructors

	public String[] getItemIds() {
		return itemIds;
	}

	public void setItemIds(String[] itemIds) {
		this.itemIds = itemIds;
	}

	public String getChargeDesc() {
		return chargeDesc;
	}

	public void setChargeDesc(String chargeDesc) {
		this.chargeDesc = chargeDesc;
	}

	/** default constructor */
	public ChargeParam() {
	}

	/** minimal constructor */
	public ChargeParam(String id) {
		this.id = id;
	}

	// Property accessors

	public String getChargeObj() {
		return chargeObj;
	}

	public void setChargeObj(String chargeObj) {
		this.chargeObj = chargeObj;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRangeCode() {
		return this.rangeCode;
	}

	public void setRangeCode(String rangeCode) {
		this.rangeCode = rangeCode;
	}

	public String getParamName() {
		return this.paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

}