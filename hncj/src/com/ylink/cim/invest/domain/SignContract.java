package com.ylink.cim.invest.domain;

// default package

import java.util.Date;

/**
 * SignContract domain. @author MyEclipse Persistence Tools
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
	
	private String payChnlTypeName;

	public String getAccreditId() {
		return accreditId;
	}

	public String getBranchNo() {
		return branchNo;
	}

	public String getBusiType() {
		return busiType;
	}

	public Date getCancelTime() {
		return this.cancelTime;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public String getCustId() {
		return this.custId;
	}

	public String getExtend() {
		return this.extend;
	}

	public String getId() {
		return this.id;
	}

	public String getInvestAcctNo() {
		return investAcctNo;
	}

	public String getPayChnlAlias() {
		return payChnlAlias;
	}
	
	public String getPayChnlNo() {
		return this.payChnlNo;
	}

	public String getPayChnlType() {
		return this.payChnlType;
	}

	public String getPayChnlTypeName() {
		return payChnlTypeName;
	}

	public String getServiceId() {
		return serviceId;
	}

	public String getState() {
		return state;
	}

	public void setAccreditId(String accreditId) {
		this.accreditId = accreditId;
	}

	public void setBranchNo(String branchNo) {
		this.branchNo = branchNo;
	}

	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}

	public void setCancelTime(Date cancelTime) {
		this.cancelTime = cancelTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public void setExtend(String extend) {
		this.extend = extend;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setInvestAcctNo(String investAcctNo) {
		this.investAcctNo = investAcctNo;
	}

	public void setPayChnlAlias(String payChnlAlias) {
		this.payChnlAlias = payChnlAlias;
	}

	public void setPayChnlNo(String payChnlNo) {
		this.payChnlNo = payChnlNo;
	}

	public void setPayChnlType(String payChnlType) {
		this.payChnlType = payChnlType;
	}

	public void setPayChnlTypeName(String payChnlTypeName) {
		this.payChnlTypeName = payChnlTypeName;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public void setState(String state) {
		this.state = state;
	}

}