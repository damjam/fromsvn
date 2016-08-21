package com.ylink.cim.admin.dao;

import java.util.List;

import com.ylink.cim.admin.domain.RoleInfo;
import com.ylink.cim.admin.domain.UserRole;

import flink.hibernate.BaseDao;
import flink.util.Pager;
import flink.util.Paginater;

public interface UserRoleDao extends BaseDao {

	public Paginater getUserRolePageList(UserRole userRole, Pager pager);

	/**
	 * 获取待分配的角色
	 * 
	 * @param userId
	 * @return
	 */
	public Paginater getWaitAssignRolePageList(String userId, Pager pager);

	List<UserRole> getUserRoleByUser(String userID);

	List<RoleInfo> getRoleByUser(String userId);

	public Paginater getAvailableRoles(String userId, Pager pager);

	public void delRoleByUser(String userId);

	public List<UserRole> getUserRoleByRole(String roleId);
}
