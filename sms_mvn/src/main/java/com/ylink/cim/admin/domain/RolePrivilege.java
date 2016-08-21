package com.ylink.cim.admin.domain;

import java.io.Serializable;

public class RolePrivilege implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private RolePrivilegeId id;

	public RolePrivilege() {
	}

	public RolePrivilege(RolePrivilegeId id) {
		this.setId(id);
	}

	public RolePrivilegeId getId() {
		return this.id;
	}

	public void setId(RolePrivilegeId id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RolePrivilege other = (RolePrivilege) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
