package com.ylink.cim.manage.dao;

import java.util.List;
import java.util.Map;

import com.ylink.cim.manage.domain.ChargeItem;

import flink.hibernate.BaseDao;
import flink.util.Pager;
import flink.util.Paginater;

public interface ChargeItemDao extends BaseDao {

	public List<ChargeItem> findBy(Map<String, Object> params);

	public List<Long> findItemNum(Map<String, Object> params);

	public Paginater findPager(Map<String, Object> params, Pager pager);
}
