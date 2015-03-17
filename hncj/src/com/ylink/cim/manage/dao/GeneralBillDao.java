package com.ylink.cim.manage.dao;

import java.util.List;
import java.util.Map;

import com.ylink.cim.manage.domain.GeneralBill;

import flink.hibernate.BaseDao;
import flink.util.Pager;
import flink.util.Paginater;

public interface GeneralBillDao extends BaseDao {

	public Paginater findBillPager(Map<String, Object> params, Pager pager);
	
	public List<GeneralBill> findBills(Map<String, Object> params);

	public Map<String, Object> findSumInfo(Map<String, Object> map);

}
