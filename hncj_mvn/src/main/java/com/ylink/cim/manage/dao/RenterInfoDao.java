package com.ylink.cim.manage.dao;

import java.util.List;
import java.util.Map;

import com.ylink.cim.manage.domain.RenterInfo;

import flink.hibernate.BaseDao;
import flink.util.Pager;
import flink.util.Paginater;

public interface RenterInfoDao extends BaseDao {

	Paginater findPaginater(Map<String, Object> params, Pager pager);

	List<RenterInfo> findList(Map<String, Object> map);
}
