package com.ylink.cim.manage.dao;

import java.util.Map;

import flink.hibernate.BaseDao;
import flink.util.Pager;
import flink.util.Paginater;

public interface CarModelDao extends BaseDao {

	public Paginater findPaginater(Map<String, Object> map, Pager pager);
	
	public Paginater findByKeyword(String keyword, Pager pager);

	public boolean isExist(String name, String brand);

}
