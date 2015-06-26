package com.ylink.cim.busioper.dao;

import java.util.Map;

import flink.hibernate.BaseDao;
import flink.util.Pager;
import flink.util.Paginater;

public interface NoticeMsgDao extends BaseDao {

	Paginater findPaginater(Map<String, Object> map, Pager pager);
}
