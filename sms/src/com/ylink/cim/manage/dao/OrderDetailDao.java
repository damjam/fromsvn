package com.ylink.cim.manage.dao;

import java.util.List;
import java.util.Map;

import com.ylink.cim.manage.domain.OrderDetail;

import flink.hibernate.BaseDao;
import flink.util.Pager;
import flink.util.Paginater;

public interface OrderDetailDao extends BaseDao {

	public Paginater findPaginater(Map<String, Object> map, Pager pager);

	public void deleteByOrderId(String id);

	public List<OrderDetail> findList(Map<String, Object> map);
}
