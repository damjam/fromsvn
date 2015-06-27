package com.ylink.cim.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ylink.cim.admin.dao.RolePrivilegeDao;
import com.ylink.cim.admin.domain.RolePrivilege;
import com.ylink.cim.admin.service.RolePrivilegeService;

@Component("rolePrivilegeService")
public class RolePrivilegeServiceImpl implements RolePrivilegeService {
	@Autowired
	private RolePrivilegeDao rolePrivilegeDao;

	@Override
	public List<RolePrivilege> getRolePrivilegeByRoleId(String roleId) {
		return this.rolePrivilegeDao.getRolePrivilegeByRoleId(roleId);
	}

	public void setRolePrivilegeDao(RolePrivilegeDao rolePrivilegeDao) {
		this.rolePrivilegeDao = rolePrivilegeDao;
	}

}
