package com.ylink.cim.admin.service;


import java.util.List;

import com.ylink.cim.admin.domain.UserInfo;

/**
 * 数据过滤服务
 * @
 *
 */
public interface IDataFilterServer {
	/**
	 * 获取用户有权限查看的所有渠道,用户用户管理方面，部门用户返回的机构为空
	 * 
	 * @param userInfo
	 * @return 若没有机构，则返回一个长度为0的list
	 */
	public List<String> getAvailableChnl(UserInfo userInfo);
	/**
	 * 获取用户有权限查看的所有分支机构,用户用户管理方面，部门用户返回的机构为空
	 * 
	 * @param userInfo
	 * @return 若没有机构，则返回一个长度为0的list
	 */
	public List<String> getAvailableBranch(UserInfo userInfo);
	
	/**
	 * 获取用户有权限查看的所有的商户
	 * @param userInfo
	 * @return 若没有商户，则返回一个长度为0的list
	 */
	public List<String> getAllAvailableMrcht(UserInfo userInfo);
	
	/**
	 * 业务管理方面的查看可用的机构，部门用户使用总部机构的权限
	 * @param userInfo
	 * @return
	 */
	public List<String> getAvailableBranchForBus(UserInfo userInfo);

}
