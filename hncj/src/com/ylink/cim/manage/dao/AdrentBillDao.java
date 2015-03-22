package com.ylink.cim.manage.dao;

import java.util.Map;

import flink.hibernate.BaseDao;
import flink.util.Pager;
import flink.util.Paginater;

public interface AdrentBillDao extends BaseDao {

	public Paginater findBillPager(Map<String, Object> params, Pager pager);
	

	public Map<String, Object> findSumInfo(Map<String, Object> map);
	
}
