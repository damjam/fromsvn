package com.ylink.cim.cust.dao;

import java.util.List;
import java.util.Map;

import com.ylink.cim.admin.domain.UserInfo;
import com.ylink.cim.cust.domain.CustInfo;

import flink.hibernate.BaseDao;
import flink.util.Pager;
import flink.util.Paginater;

public interface CustInfoDao extends BaseDao {
	public CustInfo getCustInfoByUserId(String userId);

	public CustInfo getUniqueCustInfo(String property, Object value);

	public List<CustInfo> findByParams(Map<String, Object> params);

	public Paginater findCustPager(CustInfo custInfo, UserInfo userInfo, Pager pager);

	public List<CustInfo> findForPushPlan(Map<String, Object> params, Pager pager);

	public List<CustInfo> findByParamsForNotice(Map<String, Object> params, Pager pager);
}
