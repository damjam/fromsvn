package com.ylink.cim.admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ylink.cim.admin.dao.UserLogDao;
import com.ylink.cim.admin.domain.UserInfo;
import com.ylink.cim.admin.domain.UserLog;

import flink.consant.Constants;
import flink.etc.Assert;
import flink.etc.BizException;
import flink.util.Pager;
import flink.util.Paginater;

@Component("userLogService")
public class UserLogServiceImpl implements
		com.ylink.cim.admin.service.UserLogService {

	@Autowired
	private UserLogDao userLogDao;

	@Override
	public Paginater getUserLogPageList(UserLog userLog, Pager pager,
			UserInfo userInfo) throws BizException {

		try {
			return this.userLogDao.getUserLogPageList(userLog, pager, userInfo);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BizException(e.getMessage());
		}
	}

	@Override
	public void saveUserLog(UserLog userLog) throws BizException {
		// 检查数据
		Assert.notEmpty(userLog.getUserId(), "所属用户编号不能为空");
		// Assert.notEmpty(userLog.getBranchNo(), "所属机构编号不能为空");
		// Assert.notEmpty(userLog.getLimitId(), "所属模块编号不能为空");
		Assert.notEmpty(userLog.getLogType(), "日志类类型不能为空");

		if (Constants.LOG_USER_TYPE.indexOf(userLog.getLogType()) < 0) {
			throw new BizException("日志类型非法");
		}

		Assert.notEmpty(userLog.getContent(), "日志内容不能为空");

		this.userLogDao.save(userLog);
	}

	public void setUserLogDao(UserLogDao userLogDao) throws BizException {

		this.userLogDao = userLogDao;
	}

	@Override
	public UserLog getUserLogDtl(String id) throws BizException {
		return userLogDao.findById(id);
	}

}
