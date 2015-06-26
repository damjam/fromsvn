package com.ylink.cim.admin.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ylink.cim.admin.dao.UserInfoDao;
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

	public void assignUserRole(String[] roles, String userId) throws BizException {
		/*if(roles == null){roles = new String[0];}
		List<UserRole> oldRoles = userRoleDao.getUserRoleByUser(userId);
		List<String> oldRoleIds = new ArrayList<String>();
		for (UserRole role : oldRoles) {
			oldRoleIds.add(role.getRoleId());
		}
		List<String> delRoleIds = new ArrayList<String>();// 要删除的角色id
		List<String> addRoleIds = new ArrayList<String>();// 要新增的角色id
		DistinguishDelToAdd.distinguish(oldRoleIds, DistinguishDelToAdd.StringToList(roles),
				delRoleIds, addRoleIds);

		// 处理用户的角色绑定
		this.dealUserRole(delRoleIds, addRoleIds, userId);

		// 处理用户快捷菜单
		this.autoQuickMenu(roles, userId);*/
	}
	
	


	public UserInfo getUserInfo(String userId) {
		return this.userInfoDao.getUserInfoById(userId);
	}

	/**
	 * 更新用户
	 */
	public void updateUserInfo(UserInfo userInfo) throws BizException {
		this.userInfoDao.update(userInfo);
	}

	

	public String saveUserInfo(Map<String, String> map,UserInfo operUser) throws BizException {
		//0,判断用户名是否重复
		String loginId = map.get("loginId");
		
		// 1，保存用户
		UserInfo user = new UserInfo();
		user.setLoginId(loginId);
		

		this.userInfoDao.save(user);


		String result = "编号："+loginId+"  密码：";
		return result;
	}
	

	public void setUserInfoDao(final UserInfoDao userInfoDao) {
		this.userInfoDao = userInfoDao;
	}


	/**
	 * @see com.ylink.cim.admin.service.UserService#updateUserInfo(java.util.Map)
	 */
	public void updateUserInfo(final Map<String, String> map,UserInfo operUser) throws BizException{
		String userId = map.get("userId");
		
		//0,判断用户名是否重复
		String loginId = map.get("loginId");
		
		UserInfo user = (UserInfo)userInfoDao.findById(userId);
		if(user == null){
			throw new BizException(userId+"对应的用户不存在！");
		}
		
		this.userInfoDao.update(user);
	}

	
	public Paginater getUserInfoPageList(UserInfo userInfo, Pager pager) {
		return this.userInfoDao.getUserInfoPageList(userInfo, pager);
	}

	public Paginater getPopUpUserInfoPageList(UserInfo userInfo, Pager pager) {
		return this.userInfoDao.getPopUpUserInfoPageList(userInfo, pager);
	}
	 
	public boolean isExistLoginId(String loginId) throws BizException {
		 
		return null!=this.userInfoDao.getUserInfoByLoginId(loginId);
	}
	
	public boolean isExistUserId(String userId) throws BizException {
		 
		return null!=this.userInfoDao.getUserInfoById(userId);
	}
	
	public UserInfo getUserInfoByLoginId(String loginId) throws BizException {
		
		return this.userInfoDao.getUserInfoByLoginId(loginId);
	}
	public List<IPrivilege> getAllPriv(String userId) throws BizException {
		
		return this.userInfoDao.getAllPriv(userId);
	}
	public List<WebResource> getPrivilegeResources(List<IPrivilege> allPriv)
			throws BizException {
		return this.userInfoDao.getPrivilegeResources(allPriv);
	}
	
	public void saveUserInfo(UserInfo userInfo) throws BizException {
		try{
			userInfo.setLoginPwd(MD5Util.MD5("111111"));
			this.userInfoDao.save(userInfo);
		}catch (Exception e) {
			ExceptionUtils.logBizException(UserInfoDaoImpl.class, e.getMessage());
		}
	}

	public void deleteUserInfo(String userId) throws BizException {
		
		this.userInfoDao.deleteById(userId);
	}
	public boolean isExistLoginIdExpellUserId(String loginId, String userId)
			throws BizException {

		return this.userInfoDao.isExistLoginIdExpellUserId(loginId, userId);
	}

	public void resetCustomPwd(String userId, String newPwd) throws BizException {
		userInfoDao.changePwd(userId, newPwd);
	}

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
			if((now.getTime() - lastErrorTime.getTime())/(60*1000) < 30){
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
