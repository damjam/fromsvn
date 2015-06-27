package com.ylink.cim.admin.service;

import com.ylink.cim.admin.domain.SysLog;

import flink.etc.BizException;
import flink.util.Pager;
import flink.util.Paginater;

public interface SysLogService {

	public SysLog getSysLogById(Long id) throws BizException;

	/**
	 * 获取系统日志
	 * 
	 * @param syslog
	 * @param pager
	 */
	public Paginater getSysLogPageList(SysLog syslog, Pager pager)
			throws BizException;

	/**
	 * 保存系统日志
	 * 
	 * @param sysLog
	 */
	public void saveSysLog(SysLog sysLog) throws BizException;

	public void updateSysLog(SysLog sysLog) throws BizException;

	/**
	 * 写错误日志, 日志类型为"错误", 日志种类为"系统管理员", 保存出错不抛异常.
	 * 
	 * @param content
	 */
	public void writeErrorSyslog(String content);

	/**
	 * 写信息日志, 日志类型为"信息", 日志种类为"系统管理员", 保存出错不抛异常.
	 * 
	 * @param content
	 */
	public void writeInfoSyslog(String content);
}
