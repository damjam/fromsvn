package com.ylink.cim.manage.dao;

import java.util.Map;

import com.ylink.cim.admin.domain.UserInfo;

import flink.hibernate.BaseDao;
import flink.util.Pager;
import flink.util.Paginater;

public interface MaterialDao extends BaseDao {

	public Paginater findPaginater(Map<String, Object> map, UserInfo userInfo, Pager pager);
	
	
}
