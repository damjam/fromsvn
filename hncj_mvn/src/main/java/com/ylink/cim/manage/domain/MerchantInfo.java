package com.ylink.cim.manage.domain;

import java.util.Date;


/**
 * MerchantInfo domain. @author MyEclipse Persistence Tools
 */

public class MerchantInfo extends BranchField implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields

	private String id;
	private String mrname;
	private String addr;
	private String tel;
	private String mobile;
	private String manager;
	private String seller;
	private String industry;
	private String busiScope;
	private Date createDate;
	private Date updateDate;
	private String branchNo;
	private String remark;

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getBusiScope() {
		return busiScope;
	}

	public void setBusiScope(String busiScope) {
		this.busiScope = busiScope;
	}

	@Override
	public String getBranchNo() {
		return branchNo;
	}

	@Override
	public void setBranchNo(String branchNo) {
		this.branchNo = branchNo;
	}

	// Constructors

	/** default constructor */
	public MerchantInfo() {
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMrname() {
		return mrname;
	}

	public void setMrname(String mrname) {
		this.mrname = mrname;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getManager() {
		return this.manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public String getSeller() {
		return this.seller;
	}

	public void setSeller(String seller) {
		this.seller = seller;
	}

	public String getIndustry() {
		return this.industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
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

}