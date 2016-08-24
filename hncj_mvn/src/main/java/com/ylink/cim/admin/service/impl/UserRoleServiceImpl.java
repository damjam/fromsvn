package com.ylink.cim.admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ylink.cim.admin.dao.UserRoleDao;
import com.ylink.cim.admin.domain.UserRole;
import com.ylink.cim.admin.domain.UserRoleId;
import com.ylink.cim.admin.service.UserRoleService;

import flink.etc.BizException;
import flink.util.ExceptionUtils;
import flink.util.Pager;
import flink.util.Paginater;

@Component("userRoleService")
public class UserRoleServiceImpl implements UserRoleService {

	@Autowired
	private UserRoleDao userRoleDao;

	@Override
	public void deleteUserRole(UserRoleId id) throws BizException {
		try {
			this.userRoleDao.deleteById(id);
		} catch (Exception e) {
			ExceptionUtils.logBizException(UserRoleServiceImpl.class,
					e.getMessage());
		}
	}

	@Override
	public Paginater getUserRolePageList(UserRole userRole, Pager pager)
			throws BizException {

		return this.userRoleDao.getUserRolePageList(userRole, pager);
	}

	@Override
	public Paginater getWaitAssignRolePageList(String userId, Pager pager)
			throws BizException {

		return this.userRoleDao.getWaitAssignRolePageList(userId, pager);
	}

	@Override
	public void saveUserRole(String userId, String[] roleIds)
			throws BizException {
		userRoleDao.delRoleByUser(userId);
		for (int i = 0; i < roleIds.length; i++) {
			UserRole userRole = new UserRole();
			UserRoleId id = new UserRoleId();
			id.setRoleId(roleIds[i]);
			id.setUserId(userId);
			userRole.setId(id);
			userRoleDao.saveOrUpdate(userRole);
		}
	}

	public void saveUserRole(UserRole userRole) throws BizException {

		try {
			this.userRoleDao.save(userRole);
		} catch (Exception e) {
			ExceptionUtils.logBizException(UserRoleServiceImpl.class,
					e.getMessage());
		}

	}

	public void setUserRoleDao(UserRoleDao userRoleDao) {
		this.userRoleDao = userRoleDao;
	}

}
