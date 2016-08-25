package com.ylink.cim.admin.service;

import com.ylink.cim.admin.domain.SysLog;

import flink.etc.BizException;
import flink.util.Pager;
import flink.util.Paginater;

public interface SysLogService {

	public SysLog getSysLogById(Long id) throws BizException;

	public Paginater getSysLogPageList(SysLog syslog, Pager pager)
			throws BizException;

	public void saveSysLog(SysLog sysLog) throws BizException;

	public void updateSysLog(SysLog sysLog) throws BizException;

	public void writeErrorSyslog(String content);

	public void writeInfoSyslog(String content);
}
