package com.ylink.cim.invest.domain;

// default package

import java.util.Date;

/**
 * ServiceRecord entity. @author MyEclipse Persistence Tools
 */

public class ServiceRecord implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String custId;
	private String busiType;
	private Date createTime;
	private Date cancelTime;
	private String state;
	private String branchNo;

	// Constructors

	/** default constructor */
	public ServiceRecord() {
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getBusiType() {
		return this.busiType;
	}

	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getCancelTime() {
		return this.cancelTime;
	}

	public void setCancelTime(Date cancelTime) {
		this.cancelTime = cancelTime;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getBranchNo() {
		return this.branchNo;
	}

	public void setBranchNo(String branchNo) {
		this.branchNo = branchNo;
	}

}