package com.ylink.cim.manage.dao;

import java.util.Map;

import flink.hibernate.BaseDao;
import flink.util.Pager;
import flink.util.Paginater;

public interface EmployeeDao extends BaseDao {

	public Paginater findPager(Map<String, Object> map, Pager pager);
}
