package com.ylink.cim.manage.dao.impl;

import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.hibernate.criterion.MatchMode;
import org.springframework.stereotype.Component;

import com.ylink.cim.manage.dao.OrderRecordDao;
import com.ylink.cim.manage.domain.OrderRecord;

import flink.hibernate.BaseDaoImpl;
import flink.hibernate.QueryHelper;
import flink.util.Pager;
import flink.util.Paginater;
@Component("orderRecordDao")
public class OrderRecordDaoImpl extends BaseDaoImpl implements OrderRecordDao {

	@Override
	protected Class getModelClass() {
		return OrderRecord.class;
	}

	@Override
	public Paginater findPaginater(Map<String, Object> map, Pager pager) {
		QueryHelper helper = new QueryHelper();
		helper.append("from OrderRecord where 1=1");
		helper.append("and clientName like ?", MapUtils.getString(map, "clientName"), MatchMode.ANYWHERE);
		helper.append("and clientTel like ?",MapUtils.getString(map, "clientTel"), MatchMode.START);
		helper.append("and orderDate >= ?", MapUtils.getString(map, "beginOrderDate"));
		helper.append("and orderDate <= ?", MapUtils.getString(map, "endOrderDate"));
		helper.append("and payState = ?", MapUtils.getString(map, "payState"));
		helper.append("and branchNo = ?", MapUtils.getString(map, "branchNo"));
		helper.append("order by id desc");
		return super.getPageData(helper, pager);
	}

}
