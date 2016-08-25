package com.ylink.cim.admin.service;

import com.ylink.cim.admin.domain.UserInfo;
import com.ylink.cim.admin.domain.UserLog;

import flink.etc.BizException;
import flink.util.Pager;
import flink.util.Paginater;

public interface UserLogService {

	/**
	 * 
	 * @return
	 */
	public Paginater getUserLogPageList(UserLog userLog, Pager pager,
			UserInfo userInfo) throws BizException;

	/**
	 * 
	 * @param userLog
	 */
	public void saveUserLog(UserLog userLog) throws BizException;

	public UserLog getUserLogDtl(String id) throws BizException;
}
