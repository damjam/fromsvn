package com.ylink.cim.manage.dao;

import java.util.List;
import java.util.Map;

import com.ylink.cim.manage.domain.ParkingInfo;

import flink.hibernate.BaseDao;
import flink.util.Pager;
import flink.util.Paginater;

public interface ParkingInfoDao extends BaseDao {

	public Paginater findPager(Map<String, Object> params, Pager pager);

	public List<ParkingInfo> findBy(Map<String, Object> params);
}
