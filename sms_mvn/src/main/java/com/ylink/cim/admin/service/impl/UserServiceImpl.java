package com.ylink.cim.admin.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ylink.cim.admin.dao.UserInfoDao;
import com.ylink.cim.admin.dao.UserRoleDao;
import com.ylink.cim.admin.dao.impl.UserInfoDaoImpl;
import com.ylink.cim.admin.domain.UserInfo;
import com.ylink.cim.admin.service.UserService;

import flink.MD5Util;
import flink.etc.BizException;
import flink.util.ExceptionUtils;
import flink.util.IPrivilege;
import flink.util.Pager;
import flink.util.Paginater;
import flink.util.WebResource;

@Component("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserInfoDao userInfoDao;
	@Autowired
	private UserRoleDao userRoleDao;


	@Override
	public UserInfo getUserInfo(String userId) {
		return this.userInfoDao.getUserInfoById(userId);
	}

	/**
	 * �����û�
	 */
	@Override
	public void updateUserInfo(UserInfo userInfo) throws BizException {
		this.userInfoDao.update(userInfo);
	}

	public String saveUserInfo(Map<String, String> map, UserInfo operUser)
			throws BizException {
		// 0,�ж��û����Ƿ��ظ�
		String loginId = map.get("loginId");

		// 1�������û�
		UserInfo user = new UserInfo();
		user.setLoginId(loginId);

		this.userInfoDao.save(user);

		String result = "��ţ�" + loginId + "  ���룺";
		return result;
	}

	public void setUserInfoDao(final UserInfoDao userInfoDao) {
		this.userInfoDao = userInfoDao;
	}

	/**
	 * @see com.ylink.cim.admin.service.UserService#updateUserInfo(java.util.Map)
	 */
	public void updateUserInfo(final Map<String, String> map, UserInfo operUser)
			throws BizException {
		String userId = map.get("userId");

		// 0,�ж��û����Ƿ��ظ�
		String loginId = map.get("loginId");

		UserInfo user = (UserInfo) userInfoDao.findById(userId);
		if (user == null) {
			throw new BizException(userId + "��Ӧ���û������ڣ�");
		}

		this.userInfoDao.update(user);
	}

	@Override
	public Paginater getUserInfoPageList(UserInfo userInfo, Pager pager) {
		return this.userInfoDao.getUserInfoPageList(userInfo, pager);
	}

	@Override
	public Paginater getPopUpUserInfoPageList(UserInfo userInfo, Pager pager) {
		return this.userInfoDao.getPopUpUserInfoPageList(userInfo, pager);
	}

	@Override
	public boolean isExistLoginId(String loginId) throws BizException {

		return null != this.userInfoDao.getUserInfoByLoginId(loginId);
	}

	@Override
	public boolean isExistUserId(String userId) throws BizException {

		return null != this.userInfoDao.getUserInfoById(userId);
	}

	@Override
	public UserInfo getUserInfoByLoginId(String loginId) throws BizException {

		return this.userInfoDao.getUserInfoByLoginId(loginId);
	}

	@Override
	public List<IPrivilege> getAllPriv(String userId) throws BizException {

		return this.userInfoDao.getAllPriv(userId);
	}

	@Override
	public List<WebResource> getPrivilegeResources(List<IPrivilege> allPriv)
			throws BizException {
		return this.userInfoDao.getPrivilegeResources(allPriv);
	}

	@Override
	public void saveUserInfo(UserInfo userInfo) throws BizException {
		try {
			userInfo.setLoginPwd(MD5Util.MD5("111111"));
			this.userInfoDao.save(userInfo);
		} catch (Exception e) {
			ExceptionUtils.logBizException(UserInfoDaoImpl.class,
					e.getMessage());
		}
	}

	@Override
	public void deleteUserInfo(String userId) throws BizException {
		this.userInfoDao.deleteById(userId);
		userRoleDao.delRoleByUser(userId);
	}

	@Override
	public boolean isExistLoginIdExpellUserId(String loginId, String userId)
			throws BizException {

		return this.userInfoDao.isExistLoginIdExpellUserId(loginId, userId);
	}

	@Override
	public void resetCustomPwd(String userId, String newPwd)
			throws BizException {
		userInfoDao.changePwd(userId, newPwd);
	}

	@Override
	public Integer updateErrorInfo(String userId) {
		UserInfo userInfo = userInfoDao.findByIdWithLock(userId);
		Date now = new Date();
		Date errorTime = null;
		Date lastErrorTime = userInfo.getErrorTime();
		Integer errorTimes = userInfo.getPwdErrorTimes();
		if (errorTimes == null) {
			errorTimes = 0;
		}
		if (lastErrorTime != null) {
			if ((now.getTime() - lastErrorTime.getTime()) / (60 * 1000) < 30) {
				errorTime = lastErrorTime;
			} else {
				errorTime = now;
				errorTimes = 0;
			}
		} else {
			errorTime = now;
		}
		errorTimes += 1;
		if (errorTimes > 3) {
			errorTimes = 3;
		}
		userInfo.setErrorTime(errorTime);
		userInfo.setPwdErrorTimes(errorTimes);
		userInfoDao.update(userInfo);
		return errorTimes;
	}

}
