package com.ylink.cim.invest.domain;

// default package

import java.util.Date;

/**
 * SignContract entity. @author MyEclipse Persistence Tools
 */

public class SignContract implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String custId;
	private String payChnlType;
	private String payChnlNo;
	private Date createTime;
	private String extend;
	private Date cancelTime;
	private String state;
	private String busiType;
	private String payChnlAlias;
	private String branchNo;
	private String serviceId;
	private String investAcctNo;
	private String accreditId;
	
	public String getAccreditId() {
		return accreditId;
	}

	public void setAccreditId(String accreditId) {
		this.accreditId = accreditId;
	}

	public String getInvestAcctNo() {
		return investAcctNo;
	}

	public void setInvestAcctNo(String investAcctNo) {
		this.investAcctNo = investAcctNo;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getBranchNo() {
		return branchNo;
	}

	public void setBranchNo(String branchNo) {
		this.branchNo = branchNo;
	}

	public String getPayChnlAlias() {
		return payChnlAlias;
	}

	public void setPayChnlAlias(String payChnlAlias) {
		this.payChnlAlias = payChnlAlias;
	}

	private String payChnlTypeName;
	
	public String getPayChnlTypeName() {
		return payChnlTypeName;
	}

	public void setPayChnlTypeName(String payChnlTypeName) {
		this.payChnlTypeName = payChnlTypeName;
	}

	public String getBusiType() {
		return busiType;
	}

	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}

	public String getId() {
		return this.id;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
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

	public String getPayChnlType() {
		return this.payChnlType;
	}

	public void setPayChnlType(String payChnlType) {
		this.payChnlType = payChnlType;
	}

	public String getPayChnlNo() {
		return this.payChnlNo;
	}

	public void setPayChnlNo(String payChnlNo) {
		this.payChnlNo = payChnlNo;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getExtend() {
		return this.extend;
	}

	public void setExtend(String extend) {
		this.extend = extend;
	}

	public Date getCancelTime() {
		return this.cancelTime;
	}

	public void setCancelTime(Date cancelTime) {
		this.cancelTime = cancelTime;
	}

}