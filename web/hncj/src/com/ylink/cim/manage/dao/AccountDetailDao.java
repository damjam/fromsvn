package com.ylink.cim.manage.dao;

import java.util.Map;

import flink.hibernate.BaseDao;
import flink.util.Pager;
import flink.util.Paginater;

public interface AccountDetailDao extends BaseDao {

	public Paginater findPager(Map<String, Object> params, Pager pager);
	
}
