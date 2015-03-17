package com.ylink.cim.manage.domain;

import java.util.Date;

// default package


/**
 * MerchantInfo entity. @author MyEclipse Persistence Tools
 */

public class MerchantInfo implements java.io.Serializable {

	// Fields

	private String id;
	private String name;
	private String add;
	private String tel;
	private String mobile;
	private String manager;
	private String seller;
	private String industry;
	private Date createDate;
	private Date updateDate;
	private String branchNo;
	
	public String getBranchNo() {
		return branchNo;
	}



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

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAdd() {
		return this.add;
	}

	public void setAdd(String add) {
		this.add = add;
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