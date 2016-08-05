package com.ylink.cim.manage.dao.impl;

import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.poi.hssf.record.chart.BeginRecord;
import org.springframework.stereotype.Component;

import com.ylink.cim.manage.dao.DailyOrderDao;
import com.ylink.cim.manage.domain.DailyOrder;

import flink.hibernate.BaseDaoImpl;
import flink.hibernate.QueryHelper;
import flink.util.Pager;
import flink.util.Paginater;
@Component("dailyOrderDao")
public class DailyOrderDaoImpl extends BaseDaoImpl implements DailyOrderDao {

	@Override
	protected Class getModelClass() {
		return DailyOrder.class;
	}

	@Override
	public Paginater findPaginater(Map<String, Object> params, Pager pager) {
		QueryHelper helper = new QueryHelper();
		helper.append("from DailyOrder where 1=1");
		helper.append("and orderDate >= ?", MapUtils.getString(params, "beginDate"));
		helper.append("and orderDate <= ?", MapUtils.getString(params, "endDate"));
		helper.append("order by orderDate desc");
		return super.getPageData(helper, pager);
	}
}
