package com.ylink.cim.admin.dao;

import java.util.List;

import com.ylink.cim.admin.domain.RoleInfo;

import flink.hibernate.BaseDao;
import flink.util.Pager;
import flink.util.Paginater;

public interface RoleInfoDao extends BaseDao {

	public Paginater getRoleInfoPageList(RoleInfo roleInfo,Pager pager);
	
	public void deleteRoleLimits(RoleInfo roleInfo);
	
	public RoleInfo getRoleInfo(RoleInfo roleInfo);
	public List<RoleInfo> queryRoleInfoByLimitGroupId(String limitGroupId);
}
