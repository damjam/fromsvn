package com.ylink.cim.manage.view;

import org.apache.struts.action.ActionForm;

public class MerchantInfoActionForm extends ActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String mrname;
	private String addr;
	private String tel;
	private String mobile;
	private String manager;
	private String seller;
	private String industry;
	private String busiScope;
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
	
	public String getSeller() {
		return seller;
	}
	public String getTel() {
		return tel;
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
	public void setSeller(String seller) {
		this.seller = seller;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	
	
}
