package com.ylink.cim.admin.domain;

public class RoleInfo implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String roleId;
	private String roleName;
	private String limitGroupId;
	private String limitGroupName;
	private String remark;

	private String[] limitIds;

	public static final String NORMER_CUST = "普通客户";
	public static final String MEMBER_CUST = "会员客户";

	public RoleInfo() {
	}

	public String getRoleId() {
		return this.roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getLimitGroupId() {
		return this.limitGroupId;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String[] getLimitIds() {
		return limitIds;
	}

	public void setLimitIds(String[] limitIds) {
		this.limitIds = limitIds;
	}

}