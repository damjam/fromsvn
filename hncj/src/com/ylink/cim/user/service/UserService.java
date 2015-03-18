package com.ylink.cim.user.service;

import java.util.List;

import com.ylink.cim.user.domain.UserInfo;

import flink.etc.BizException;
import flink.util.IPrivilege;
import flink.util.Pager;
import flink.util.Paginater;
import flink.util.WebResource;

public interface UserService {

	public void assignUserRole(String[] roles, String userId) throws BizException;

	public void updateUserInfo(UserInfo userInfo) throws BizException;

	public UserInfo getUserInfo(String userId);

	public UserInfo getUserInfoByLoginId(String loginId) throws BizException;

	public Paginater getUserInfoPageList(UserInfo userInfo, Pager pager);

	public Paginater getPopUpUserInfoPageList(UserInfo userInfo, Pager pager);

	public boolean isExistLoginId(String loginId) throws BizException;

	public boolean isExistUserId(String userId) throws BizException;

	public List<IPrivilege> getAllPriv(String userId) throws BizException;

	public List<WebResource> getPrivilegeResources(List<IPrivilege> allPriv) throws BizException;

	public void saveUserInfo(UserInfo userInfo) throws BizException;

	public void deleteUserInfo(String userId) throws BizException;

	public boolean isExistLoginIdExpellUserId(String loginId, String userId) throws BizException;

	public void resetCustomPwd(String userId, String newPwd) throws BizException;

	public Integer updateErrorInfo(String userId);
}
