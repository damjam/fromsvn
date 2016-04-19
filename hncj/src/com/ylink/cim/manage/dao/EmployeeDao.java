package com.ylink.cim.manage.dao;

import java.util.List;
import java.util.Map;

import com.ylink.cim.manage.domain.Employee;

import flink.hibernate.BaseDao;
import flink.util.Pager;
import flink.util.Paginater;

public interface EmployeeDao extends BaseDao {

	public Paginater findPager(Map<String, Object> map, Pager pager);

	public List<Employee> findExist(Map<String, Object> params);
}
