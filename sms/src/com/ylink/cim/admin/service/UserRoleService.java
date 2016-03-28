package com.ylink.cim.admin.service;

import com.ylink.cim.admin.domain.UserRole;
import com.ylink.cim.admin.domain.UserRoleId;

import flink.etc.BizException;
import flink.util.Pager;
import flink.util.Paginater;

public interface UserRoleService {

	public void deleteUserRole(UserRoleId id) throws BizException;

	public Paginater getUserRolePageList(UserRole userRole, Pager pager)
			throws BizException;

	public Paginater getWaitAssignRolePageList(String userId, Pager pager)
			throws BizException;

	public void saveUserRole(String userId, String[] roleIds)
			throws BizException;

}
