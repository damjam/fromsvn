package com.ylink.cim.manage.dao;

import java.util.List;
import java.util.Map;

import com.ylink.cim.manage.domain.CommonServiceBill;

import flink.hibernate.BaseDao;
import flink.util.Pager;
import flink.util.Paginater;

public interface CommonServiceBillDao extends BaseDao {

	public Paginater findPager(Map<String, Object> params, Pager pager);

	public Map<String, Object> findSumInfo(Map<String, Object> map);
	
	public List<CommonServiceBill> getAllLastBill(Map<String, Object> map);
	
}
