package com.ylink.cim.admin.domain;

import java.io.Serializable;

public class UserRole implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private UserRoleId id = new UserRoleId();

	private String userName;

	private String loginId;

	private String roleName;

	private String userType;

	private String[] roleIds;

	private String userId;

	private String roleId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String[] getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String[] roleIds) {
		this.roleIds = roleIds;
	}

	public UserRoleId getId() {
		return id;
	}

	public void setId(UserRoleId id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

}
