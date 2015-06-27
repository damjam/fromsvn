package com.ylink.cim.admin.dao.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.ylink.cim.admin.dao.RolePrivilegeDao;
import com.ylink.cim.admin.domain.RolePrivilege;

import flink.hibernate.BaseDaoImpl;
import flink.hibernate.QueryHelper;

@Component("rolePrivilegeDao")
public class RolePrivilegeDaoImpl extends BaseDaoImpl implements
		RolePrivilegeDao {

	@Override
	public List<RolePrivilege> getRolePrivilegeByRoleId(String roleId) {

		QueryHelper helper = new QueryHelper();
		helper.append("from RolePrivilege p");
		helper.append("where 1=1");
		helper.append("and p.id.roleId=?", roleId);

		return super.getList(helper);
	}

	@Override
	protected Class getModelClass() {
		return RolePrivilege.class;
	}

}
