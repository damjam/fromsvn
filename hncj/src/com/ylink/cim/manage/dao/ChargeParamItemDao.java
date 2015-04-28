package com.ylink.cim.manage.dao;

import java.util.List;
import java.util.Map;

import com.ylink.cim.manage.domain.ChargeParamItem;

import flink.hibernate.BaseDao;
import flink.util.Pager;
import flink.util.Paginater;

public interface ChargeParamItemDao extends BaseDao {

	public Paginater findPager(Map<String, Object> params, Pager pager);

	public List<ChargeParamItem> findBy(Map<String, Object> params);

	public void deleteByParamId(String id);
}
