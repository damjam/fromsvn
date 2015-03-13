package com.ylink.cim.admin.view;

import org.apache.struts.action.ActionForm;

public class LimitGroupInfoActionForm extends ActionForm {

	private static final long serialVersionUID = 1L;
	
	private String limitGroupId;

	private String limitGroupName;

	private String userType;
	
	private String userTypeName;
	
	private String limitIds[];

	public String getLimitGroupId() {
		return limitGroupId;
	}

	public void setLimitGroupId(String limitGroupId) {
		this.limitGroupId = limitGroupId;
	}

	public String getLimitGroupName() {
		return limitGroupName;
	}

	public void setLimitGroupName(String limitGroupName) {
		this.limitGroupName = limitGroupName;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getUserTypeName() {
		return userTypeName;
	}

	public void setUserTypeName(String userTypeName) {
		this.userTypeName = userTypeName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String[] getLimitIds() {
		return limitIds;
	}

	public void setLimitIds(String[] limitIds) {
		this.limitIds = limitIds;
	}

	
}
