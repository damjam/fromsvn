package com.ylink.cim.busioper.dao;

import java.util.Map;

import flink.hibernate.BaseDao;
import flink.util.Pager;
import flink.util.Paginater;

public interface PushPlanDao extends BaseDao{

	public Paginater findPaginater(Map<String, Object> map, Pager pager);
}
