package com.ylink.cim.admin.service;

import java.util.List;

import com.ylink.cim.admin.domain.RoleInfo;

import flink.etc.BizException;
import flink.util.Pager;
import flink.util.Paginater;

public interface RoleInfoService {

	public Paginater getRoleInfoPageList(RoleInfo roleInfo, Pager pager)
			throws BizException;

	public void deleteRoleInfo(RoleInfo roleInfo) throws BizException;

	public void saveRoleAndLimits(RoleInfo roleInfo) throws BizException;

	public RoleInfo getRoleInfoByRoleId(String roleId) throws BizException;

	public void updateRoleAndLimits(RoleInfo roleInfo) throws BizException;

	public boolean isExistRole(RoleInfo roleInfo) throws BizException;

	public List<RoleInfo> queryRoleInfoByLimitGroupId(String limitGroupId)
			throws BizException;
}
