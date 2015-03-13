package com.ylink.cim.admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ylink.cim.admin.dao.UserLogDao;
import com.ylink.cim.admin.domain.UserLog;
import com.ylink.cim.user.domain.UserInfo;

import flink.consant.Constants;
import flink.etc.Assert;
import flink.etc.BizException;
import flink.util.Pager;
import flink.util.Paginater;

@Component("userLogService")
public class UserLogServiceImpl implements com.ylink.cim.admin.service.UserLogService {

	@Autowired
	private UserLogDao userLogDao;

	public Paginater getUserLogPageList(UserLog userLog, Pager pager, UserInfo userInfo) throws BizException {

		try {
			return this.userLogDao.getUserLogPageList(userLog, pager, userInfo);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BizException(e.getMessage());
		}
	}

	public void saveUserLog(UserLog userLog) throws BizException {
		// �������
		Assert.notEmpty(userLog.getUserId(), "�����û���Ų���Ϊ��");
		// Assert.notEmpty(userLog.getBranchNo(), "����������Ų���Ϊ��");
		//Assert.notEmpty(userLog.getLimitId(), "����ģ���Ų���Ϊ��");
		Assert.notEmpty(userLog.getLogType(), "��־�����Ͳ���Ϊ��");

		if (Constants.LOG_USER_TYPE.indexOf(userLog.getLogType()) < 0) {
			throw new BizException("��־���ͷǷ�");
		}

		Assert.notEmpty(userLog.getContent(), "��־���ݲ���Ϊ��");

		this.userLogDao.save(userLog);
	}

	public void setUserLogDao(UserLogDao userLogDao) throws BizException {

		this.userLogDao = userLogDao;
	}

	public UserLog getUserLogDtl(String id) throws BizException {
		return userLogDao.findById(id);
	}
	
	

}
