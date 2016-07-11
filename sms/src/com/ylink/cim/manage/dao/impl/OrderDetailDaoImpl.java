package com.ylink.cim.manage.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Component;

import com.ylink.cim.common.state.DeliveryState;
import com.ylink.cim.manage.dao.OrderDetailDao;
import com.ylink.cim.manage.domain.OrderDetail;

import flink.hibernate.BaseDaoImpl;
import flink.hibernate.HqlHelper;
import flink.hibernate.QueryHelper;
import flink.util.Pager;
import flink.util.Paginater;
@Component("orderDetailDao")
public class OrderDetailDaoImpl extends BaseDaoImpl implements OrderDetailDao {

	@Override
	protected Class getModelClass() {
		return OrderDetail.class;
	}

	@Override
	public Paginater findPaginater(Map<String, Object> map, Pager pager) {
		QueryHelper helper = new QueryHelper();
		helper.append("from OrderDetail where orderId = ?", MapUtils.getString(map, "orderId"));
		return super.getPageData(helper, pager);
	}

	@Override
	public void deleteByOrderId(String id) {
		HqlHelper helper = new HqlHelper();
		helper.append("delete OrderDetail where orderId = ?",id);
		super.execute(helper);
	}

	@Override
	public List<OrderDetail> findList(Map<String, Object> map) {
		QueryHelper helper = new QueryHelper();
		helper.append("from OrderDetail where 1=1");
		helper.append("and orderId = ?", MapUtils.getString(map, "orderId"));
		helper.append("and state = ?", MapUtils.getString(map, "state"));
		helper.append("and state <> ?", MapUtils.getString(map, "excludeState"));
		return super.getList(helper);
	}

	@Override
	public List<OrderDetail> findUndelis(String orderId) {
		QueryHelper helper = new QueryHelper();
		helper.append("from OrderDetail where 1=1");
		helper.append("and orderId = ?", orderId);
		helper.append("and (state = ?", DeliveryState.INIT.getValue());
		helper.append("or state = ?)", DeliveryState.SENT.getValue());
		return super.getList(helper);
	}

}
