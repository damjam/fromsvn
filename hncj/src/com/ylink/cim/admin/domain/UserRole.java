package com.ylink.cim.admin.domain;

import java.io.Serializable;

public class UserRole implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private UserRoleId id;
	
	private String userName;
	
	private String loginId;
	
	private String roleName;
	
	private String userType;

	public UserRoleId getId() {
		return id;
	}

	public String getLoginId() {
		return loginId;
	}

	public String getRoleName() {
		return roleName;
	}

	public String getUserName() {
		return userName;
	}

	public String getUserType() {
		return userType;
	}

	public void setId(UserRoleId id) {
		this.id = id;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}
	
	

}
