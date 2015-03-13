package com.ylink.cim.admin.dao;

import com.ylink.cim.admin.domain.UserLog;
import com.ylink.cim.user.domain.UserInfo;

import flink.hibernate.BaseDao;
import flink.util.Pager;
import flink.util.Paginater;

/**
 * 
 * 
 */
public interface UserLogDao extends BaseDao {

	/**
	 * ��־�б��ѯ
	 * 
	 * @param UserLog
	 * @return
	 */
	public Paginater getUserLogPageList(UserLog UserLog, Pager pager, UserInfo userInfo) throws Exception;

	public void saveUserLog(UserLog userLog) throws Exception;

}
