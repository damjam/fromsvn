package com.ylink.cim.admin.domain;

import java.io.Serializable;

public class RolePrivilegeId implements Serializable {

	private static final long serialVersionUID = 1L;

	private java.lang.String limitId;

	private java.lang.String roleId;

	public RolePrivilegeId() {
	}

	public java.lang.String getLimitId() {
		return limitId;
	}

	public void setLimitId(java.lang.String limitId) {
		this.limitId = limitId;
	}

	public java.lang.String getRoleId() {
		return roleId;
	}

	public void setRoleId(java.lang.String roleId) {
		this.roleId = roleId;
	}

	
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((limitId == null) ? 0 : limitId.hashCode());
		result = prime * result + ((roleId == null) ? 0 : roleId.hashCode());
		return result;
	}

	
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RolePrivilegeId other = (RolePrivilegeId) obj;
		if (limitId == null) {
			if (other.limitId != null)
				return false;
		} else if (!limitId.equals(other.limitId))
			return false;
		if (roleId == null) {
			if (other.roleId != null)
				return false;
		} else if (!roleId.equals(other.roleId))
			return false;
		return true;
	}

	
}
