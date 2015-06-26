package com.ylink.cim.manage.dao;

import java.util.List;
import java.util.Map;

import com.ylink.cim.manage.domain.ChargeParamItem;

import flink.hibernate.BaseDao;
import flink.util.Pager;
import flink.util.Paginater;

public interface ChargeParamItemDao extends BaseDao {

	public void deleteByParamId(String id);

	public List<ChargeParamItem> findBy(Map<String, Object> params);

	public Paginater findPager(Map<String, Object> params, Pager pager);
}
