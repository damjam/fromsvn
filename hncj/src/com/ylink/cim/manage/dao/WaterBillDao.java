package com.ylink.cim.manage.dao;

import java.util.List;
import java.util.Map;

import com.ylink.cim.manage.domain.AccountDetail;
import com.ylink.cim.manage.domain.WaterBill;

import flink.hibernate.BaseDao;
import flink.util.Pager;
import flink.util.Paginater;

public interface WaterBillDao extends BaseDao {

	public Paginater findWaterBillPager(Map<String, Object> params, Pager pager);

	public List<WaterBill> findBills(Map<String, Object> params);

	public Map<String, Object> findSumInfo(Map<String, Object> map);

	public List findErr();

	public AccountDetail getstDeta(String id);
}
