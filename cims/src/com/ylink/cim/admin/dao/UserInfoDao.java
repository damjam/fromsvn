package com.ylink.cim.admin.dao;

import java.util.List;

import com.ylink.cim.admin.domain.UserInfo;

import flink.hibernate.BaseDao;
import flink.util.IPrivilege;
import flink.util.Pager;
import flink.util.Paginater;
import flink.util.WebResource;

public interface UserInfoDao extends BaseDao {

	public UserInfo getUserInfoByLoginId(String loginId);

	public List<IPrivilege> getAllPriv(String userId);

	public List<String> getPrivilege(String userId);

	public List<WebResource> getPrivilegeResources(List<IPrivilege> listOfPriv);

	public UserInfo getUserInfoById(String string);
	
	public Paginater getUserInfoPageList(UserInfo userInfo,Pager pager);
	
	public Paginater getPopUpUserInfoPageList(UserInfo userInfo,Pager pager);

	public boolean isExistLoginIdExpellUserId(String loginId,String userId);
	
	public void changePwd(String userId, String newPwd);
}
