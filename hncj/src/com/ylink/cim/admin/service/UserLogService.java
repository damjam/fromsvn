package com.ylink.cim.admin.service;

import com.ylink.cim.admin.domain.UserInfo;
import com.ylink.cim.admin.domain.UserLog;

import flink.etc.BizException;
import flink.util.Pager;
import flink.util.Paginater;

public interface UserLogService {

	/**
	 * 获取分页差性能数据
	 * 
	 * @return
	 */
	public Paginater getUserLogPageList(UserLog userLog, Pager pager, UserInfo userInfo) throws BizException;

	/**
	 * 保存用户日志
	 * 
	 * @param userLog
	 */
	public void saveUserLog(UserLog userLog) throws BizException;
	
	public UserLog getUserLogDtl(String id)throws BizException;
}
