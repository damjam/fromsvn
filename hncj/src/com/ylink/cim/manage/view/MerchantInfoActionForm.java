package com.ylink.cim.manage.view;

import org.apache.struts.action.ActionForm;

public class MerchantInfoActionForm extends ActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private String add;
	private String tel;
	private String mobile;
	private String manager;
	private String seller;
	private String industry;
	
	public String getAdd() {
		return add;
	}
	public String getId() {
		return id;
	}
	public String getIndustry() {
		return industry;
	}
	public String getManager() {
		return manager;
	}
	public String getMobile() {
		return mobile;
	}
	public String getName() {
		return name;
	}
	public String getSeller() {
		return seller;
	}
	public String getTel() {
		return tel;
	}
	public void setAdd(String add) {
		this.add = add;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	public void setManager(String manager) {
		this.manager = manager;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setSeller(String seller) {
		this.seller = seller;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	
	
}
