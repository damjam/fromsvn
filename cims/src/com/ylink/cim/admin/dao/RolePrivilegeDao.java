package com.ylink.cim.admin.dao;

import java.util.List;

import com.ylink.cim.admin.domain.RolePrivilege;

import flink.hibernate.BaseDao;

public interface RolePrivilegeDao extends BaseDao {

	public List<RolePrivilege> getRolePrivilegeByRoleId(String roleId);
}