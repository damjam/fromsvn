package com.ylink.cim.admin.dao;

import com.ylink.cim.admin.domain.SysLog;

import flink.hibernate.BaseDao;
import flink.util.Pager;
import flink.util.Paginater;

public interface SysLogDao extends BaseDao {

	// 获取查询结果
	public Paginater getSysLogPagList(SysLog sysLog, Pager pager)
			throws Exception;

	/**
	 * 保存日志
	 * 
	 * @param sysLog
	 * @throws Exception
	 */
	public void saveSysLog(SysLog sysLog) throws Exception;

	public void updateUserLog(SysLog sysLog) throws Exception;

}
