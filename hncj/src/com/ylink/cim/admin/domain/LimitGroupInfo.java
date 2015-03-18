package com.ylink.cim.admin.domain;


public class LimitGroupInfo implements java.io.Serializable {

	private static final long serialVersionUID = 6865297970802834110L;

	private String limitGroupId;

	private String limitGroupName;

	private String userType;
	
	private String userTypeName;
	
	private String limitIds[];


	public LimitGroupInfo() {
	}

	public LimitGroupInfo(final String limitGroupId, final String userType, final String limitGroupName) {
		this.limitGroupId = limitGroupId;
		this.userType = userType;
		this.limitGroupName = limitGroupName;
	}

	public LimitGroupInfo(final String limitGroupId, final String userType, final String limitGroupName,
		final String branchNo, final String merchantNo, final String deptId) {
		this.limitGroupId = limitGroupId;
		this.userType = userType;
		this.limitGroupName = limitGroupName;
	}

	public String getLimitGroupId() {
		return this.limitGroupId;
	}

	public String getLimitGroupName() {
		return this.limitGroupName;
	}


	public String[] getLimitIds() {
		return limitIds;
	}


	public String getUserType() {
		return this.userType;
	}

	public String getUserTypeName() {
		return userTypeName;
	}


	public void setLimitGroupId(final String limitGroupId) {
		this.limitGroupId = limitGroupId;
	}

	public void setLimitGroupName(final String limitGroupName) {
		this.limitGroupName = limitGroupName;
	}

	public void setLimitIds(String[] limitIds) {
		this.limitIds = limitIds;
	}

	public void setUserType(final String userType) {
		this.userType = userType;
	}

	public void setUserTypeName(String userTypeName) {
		this.userTypeName = userTypeName;
	}

	
}