package com.ylink.cim.manage.dao;

import java.util.List;
import java.util.Map;

import com.ylink.cim.manage.domain.AccountDetail;
import com.ylink.cim.manage.domain.ElecBill;

import flink.hibernate.BaseDao;
import flink.util.Pager;
import flink.util.Paginater;

public interface ElecBillDao extends BaseDao {

	public Paginater findBillPager(Map<String, Object> params, Pager pager);

	public List<ElecBill> findBills(Map<String, Object> params);

	public List findErr();

	public Map<String, Object> findSumInfo(Map<String, Object> map);

	public AccountDetail getstDeta(String id);
}
