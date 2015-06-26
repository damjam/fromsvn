package com.ylink.cim.admin.dao;

import com.ylink.cim.admin.domain.UserInfo;
import com.ylink.cim.admin.domain.UserLog;

import flink.hibernate.BaseDao;
import flink.util.Pager;
import flink.util.Paginater;

/**
 * 
 * 
 */
public interface UserLogDao extends BaseDao {

	/**
	 * 日志列表查询
	 * 
	 * @param UserLog
	 * @return
	 */
	public Paginater getUserLogPageList(UserLog UserLog, Pager pager, UserInfo userInfo) throws Exception;

	public void saveUserLog(UserLog userLog) throws Exception;

}
