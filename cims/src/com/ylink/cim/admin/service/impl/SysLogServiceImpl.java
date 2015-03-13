package com.ylink.cim.admin.service.impl;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ylink.cim.admin.dao.SysLogDao;
import com.ylink.cim.admin.domain.SysLog;
import com.ylink.cim.admin.service.SysLogService;

import flink.etc.Assert;
import flink.etc.BizException;
import flink.util.Pager;
import flink.util.Paginater;
@Component("sysLogService")
public class SysLogServiceImpl implements SysLogService {
	private static final String SYSTEM_LIMITID = "system";
	private static final Logger logger = Logger.getLogger(SysLogServiceImpl.class);
	@Autowired
	private SysLogDao sysLogDao;

	public SysLog getSysLogById(Long id) throws BizException {

		try {
			return (SysLog)this.sysLogDao.findById(id);
		} catch (Exception e) {
			throw new BizException(e.getMessage());
		}
	}

	public Paginater getSysLogPageList(SysLog sysLog, Pager pager) throws BizException {
		try {
			return this.sysLogDao.getSysLogPagList(sysLog, pager);
		} catch (Exception e) {
			throw new BizException(e.getMessage());
		}
	}

	public void saveSysLog(SysLog sysLog) throws BizException {

		Assert.notEmpty(sysLog.getLimitId(), "模块编号不能为空");
		Assert.notEmpty(sysLog.getLogType(), "日志类型不能为空");
		Assert.notEmpty(sysLog.getLogClass(), "日志种类不能为空");
		Assert.notEmpty(sysLog.getContent(), "日志内容不能为空");
		String content = sysLog.getContent();
		if (content != null) {
			content = content.replaceAll("\\s*|\t|\r|\n", "");
		}
		content = StringUtils.abbreviate(content, 250);
		if (null == sysLog.getCreateTime()) {
			throw new BizException("日志创建时间不能为空");
		}

		this.sysLogDao.save(sysLog);
	}

	public void setSysLogDao(SysLogDao sysLogDao) throws BizException {
		this.sysLogDao = sysLogDao;
	}

	public void updateSysLog(SysLog sysLog) throws BizException {
		try {
			this.sysLogDao.updateUserLog(sysLog);
		} catch (Exception e) {
			throw new BizException(e.getMessage());
		}
	}

	public void writeErrorSyslog(String content) {
		logger.debug(content);

		try {
			SysLog log = new SysLog();
			log.setLimitId(SYSTEM_LIMITID);
			log.setLogType("E");
			log.setLogClass("S");
			if (content != null) {
				content = content.replaceAll("\\s*|\t|\r|\n", "");
			}
			content = StringUtils.abbreviate(content, 250);
			log.setContent(content);
			log.setContent(content);
			log.setCreateTime(new Date());
			log.setState("11");

			sysLogDao.save(log);
		} catch (Exception e) {
			// 保存日志出错不抛出异常.
			logger.debug(e, e);
		}
	}

	public void writeInfoSyslog(String content) {
		logger.debug(content);
		
	}

}
