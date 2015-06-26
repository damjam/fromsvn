package com.ylink.cim.admin.service;

import java.util.List;

import com.ylink.cim.admin.domain.RolePrivilege;

public interface RolePrivilegeService {

	public List<RolePrivilege> getRolePrivilegeByRoleId(String roleId);
}
