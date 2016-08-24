package com.ylink.cim.manage.dao;

import java.util.List;
import java.util.Map;

import com.ylink.cim.manage.domain.CarInfo;

import flink.hibernate.BaseDao;
import flink.util.Pager;
import flink.util.Paginater;

public interface CarInfoDao extends BaseDao {

	public List<CarInfo> findList(Map<String, Object> params);

	public Paginater findPager(Map<String, Object> params, Pager pager);
}
