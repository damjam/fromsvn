package com.ylink.cim.cust.dao;

import java.util.List;
import java.util.Map;

import com.ylink.cim.admin.domain.UserInfo;
import com.ylink.cim.cust.domain.CustInfo;

import flink.hibernate.BaseDao;
import flink.util.Pager;
import flink.util.Paginater;

public interface CustInfoDao extends BaseDao{

	public abstract List<CustInfo> findByParams(Map<String, Object> params);

	public abstract List<CustInfo> findByParamsForNotice(
			Map<String, Object> params, Pager pager);

	public abstract Paginater findCustPager(CustInfo custInfo,
			UserInfo userInfo, Pager pager);

	public abstract List<CustInfo> findForPushPlan(Map<String, Object> params,
			Pager pager);

	public abstract CustInfo getCustInfoByUserId(String userId);

	public abstract CustInfo getUniqueCustInfo(String property, Object value);

}