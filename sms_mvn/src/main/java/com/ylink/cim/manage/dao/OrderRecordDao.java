package com.ylink.cim.manage.dao;

import java.util.List;
import java.util.Map;

import flink.hibernate.BaseDao;
import flink.util.Pager;
import flink.util.Paginater;

public interface OrderRecordDao extends BaseDao {

	public Paginater findPaginater(Map<String, Object> map, Pager pager);

	public Map<String, Object> findSumInfo(Map<String, Object> map);
	
	public List<Map<String, Object>> findDailySum(String beginDate, String endDate);
	
	
}
