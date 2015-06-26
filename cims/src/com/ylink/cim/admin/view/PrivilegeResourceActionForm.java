package com.ylink.cim.admin.view;

import org.apache.struts.action.ActionForm;

public class PrivilegeResourceActionForm extends ActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private java.lang.Long id;

    private java.lang.String limitId;
    
    private String limitName;

    private java.lang.String url;

    private java.lang.String param;

    private java.lang.String isEntry;

	public java.lang.Long getId() {
		return id;
	}

	public void setId(java.lang.Long id) {
		this.id = id;
	}

	public java.lang.String getLimitId() {
		return limitId;
	}

	public void setLimitId(java.lang.String limitId) {
		this.limitId = limitId;
	}

	public java.lang.String getUrl() {
		return url;
	}

	public void setUrl(java.lang.String url) {
		this.url = url;
	}

	public java.lang.String getParam() {
		return param;
	}

	public void setParam(java.lang.String param) {
		this.param = param;
	}

	public java.lang.String getIsEntry() {
		return isEntry;
	}

	public void setIsEntry(java.lang.String isEntry) {
		this.isEntry = isEntry;
	}

	public String getLimitName() {
		return limitName;
	}

	public void setLimitName(String limitName) {
		this.limitName = limitName;
	}

	
}
