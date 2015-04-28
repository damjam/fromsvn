package com.ylink.cim.manage.domain;
// default package

import java.util.Date;

/**
 * ChargeParam entity. @author MyEclipse Persistence Tools
 */

public class ChargeParam implements java.io.Serializable {

	// Fields

	private String id;
	private String remark;
	private String branchNo;
	private String rangeCode;
	private String chargeObj;
	private String paramName;
	private Date createDate;
	private String createUser;
	private String chargeDesc;
	
	// Constructors

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

	public String getBranchNo() {
		return this.branchNo;
	}

	public void setBranchNo(String branchNo) {
		this.branchNo = branchNo;
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