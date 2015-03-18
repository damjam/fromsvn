package com.ylink.cim.admin.view;

import org.apache.struts.action.ActionForm;

public class PrivilegeActionForm extends ActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private java.lang.String limitId;

	private java.lang.String limitName;

	private java.lang.String parent;

	private java.lang.String isMenu;

	private java.lang.Integer menuOrder;

	private java.lang.String ifAudit;
	private String entry;
	private Long id;
	private String isEntry;
	public String getIsEntry() {
		return isEntry;
	}

	public void setIsEntry(String isEntry) {
		this.isEntry = isEntry;
	}

	public Long getId() {
		if (id != null && id == 0) {
			id = null;
		}
		return id;
	}

	public void setId(Long id) {
		if (id != null && id == 0) {
			id = null;
		}
		this.id = id;
	}

	public String getEntry() {
		return entry;
	}

	public void setEntry(String entry) {
		this.entry = entry;
	}

	private String url;

	private String param;

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCode() {
		return this.limitId;
	}

	public java.lang.String getIfAudit() {
		return this.ifAudit;
	}

	public java.lang.String getIsMenu() {
		return this.isMenu;
	}

	public java.lang.String getLimitId() {
		return limitId;
	}

	public java.lang.String getLimitName() {
		return this.limitName;
	}

	public java.lang.Integer getMenuOrder() {
		return this.menuOrder;
	}

	public String getName() {
		return this.limitName;
	}

	public java.lang.String getParent() {
		return this.parent;
	}

	public void setCode(String code) {
		this.limitId = code;
	}

	public void setIfAudit(java.lang.String ifAudit) {
		this.ifAudit = ifAudit;
	}

	public void setIsMenu(java.lang.String isMenu) {
		this.isMenu = isMenu;
	}

	public void setLimitId(java.lang.String limitId) {
		this.limitId = limitId;
	}

	public void setLimitName(java.lang.String limitName) {
		this.limitName = limitName;
	}

	public void setMenuOrder(java.lang.Integer menuOrder) {
		this.menuOrder = menuOrder;
	}

	public void setParent(java.lang.String parent) {
		this.parent = parent;
	}
	
	
}
