package com.ylink.cim.admin.dao;

import java.util.List;

import com.ylink.cim.admin.domain.UserInfo;

import flink.hibernate.BaseDao;
import flink.util.IPrivilege;
import flink.util.Pager;
import flink.util.Paginater;
import flink.util.WebResource;

public interface UserInfoDao extends BaseDao {

	public void changePwd(String userId, String newPwd);

	public List<IPrivilege> getAllPriv(String userId);

	public Paginater getPopUpUserInfoPageList(UserInfo userInfo, Pager pager);

	public List<String> getPrivilege(String userId);

	public List<WebResource> getPrivilegeResources(List<IPrivilege> listOfPriv);

	public UserInfo getUserInfoById(String string);

	public UserInfo getUserInfoByLoginId(String loginId);

	public Paginater getUserInfoPageList(UserInfo userInfo, Pager pager);

	public boolean isExistLoginIdExpellUserId(String loginId, String userId);
}
