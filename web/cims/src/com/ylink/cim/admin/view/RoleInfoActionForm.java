package com.ylink.cim.admin.view;

import org.apache.struts.action.ActionForm;

public class RoleInfoActionForm extends ActionForm {

	private static final long serialVersionUID = 1L;
	
	 private String roleId;
	 private String roleName;
	 private String limitGroupId;
	 private String limitGroupName;
	 private String remark;
	 
	 private String[] limitIds;
     
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
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
