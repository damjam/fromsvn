package com.ylink.cim.manage.dao;

import java.util.List;
import java.util.Map;

import com.ylink.cim.manage.domain.ParkingBill;

import flink.hibernate.BaseDao;
import flink.util.Pager;
import flink.util.Paginater;

public interface ParkingBillDao extends BaseDao {

	public Paginater findPager(Map<String, Object> params, Pager pager);

	public Map<String, Object> findSumInfo(Map<String, Object> params);

	public List<ParkingBill> getAllLastBill(Map<String, Object> map);
}
