package com.ylink.cim.manage.dao;

import java.util.List;
import java.util.Map;

import com.ylink.cim.manage.domain.MerchantInfo;

import flink.hibernate.BaseDao;
import flink.util.Pager;
import flink.util.Paginater;

public interface MerchantInfoDao extends BaseDao {

	public Paginater findPager(Map<String, Object> params, Pager pager);

	public List<MerchantInfo> findList(Map<String, Object> params);

	public List<MerchantInfo> findByKeyword(String keyword);
}
