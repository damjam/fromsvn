package com.ylink.cim.admin.service;

import com.ylink.cim.admin.domain.SysLog;

import flink.etc.BizException;
import flink.util.Pager;
import flink.util.Paginater;

public interface SysLogService {

	public SysLog getSysLogById(Long id) throws BizException;

	/**
	 * ��ȡϵͳ��־
	 * 
	 * @param syslog
	 * @param pager
	 */
	public Paginater getSysLogPageList(SysLog syslog, Pager pager)
			throws BizException;

	/**
	 * ����ϵͳ��־
	 * 
	 * @param sysLog
	 */
	public void saveSysLog(SysLog sysLog) throws BizException;

	public void updateSysLog(SysLog sysLog) throws BizException;

	/**
	 * д������־, ��־����Ϊ"����", ��־����Ϊ"ϵͳ����Ա", ����������쳣.
	 * 
	 * @param content
	 */
	public void writeErrorSyslog(String content);

	/**
	 * д��Ϣ��־, ��־����Ϊ"��Ϣ", ��־����Ϊ"ϵͳ����Ա", ����������쳣.
	 * 
	 * @param content
	 */
	public void writeInfoSyslog(String content);
}
