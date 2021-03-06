package com.ylink.cim.manage.dao;

import java.util.List;
import java.util.Map;

import com.ylink.cim.manage.domain.OwnerInfo;

import flink.hibernate.BaseDao;
import flink.util.Pager;
import flink.util.Paginater;

public interface OwnerInfoDao extends BaseDao {

	public Paginater findPager(Map<String, Object> params, Pager pager);
	
	List<OwnerInfo> findByInfos(Map<String, Object> params);
	
	OwnerInfo getNormalOwner(String houseSn);
}
