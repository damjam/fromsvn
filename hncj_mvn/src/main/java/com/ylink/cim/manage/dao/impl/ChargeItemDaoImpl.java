package com.ylink.cim.manage.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Component;

import com.ylink.cim.manage.dao.ChargeItemDao;
import com.ylink.cim.manage.domain.ChargeItem;

import flink.hibernate.BaseDaoImpl;
import flink.hibernate.QueryHelper;
import flink.util.Pager;
import flink.util.Paginater;

@Component("chargeItemDao")
public class ChargeItemDaoImpl extends BaseDaoImpl implements ChargeItemDao {

	@Override
	public List<ChargeItem> findBy(Map<String, Object> params) {
		QueryHelper helper = new QueryHelper();
		helper.append("from ChargeItem where 1=1");
		helper.append("and way = ?", MapUtils.getString(params, "way"));
		helper.append("and item = ?", MapUtils.getString(params, "item"));
		helper.append("and itemName = ?",
				MapUtils.getString(params, "itemName"));
		helper.append("and id <> ?", MapUtils.getString(params, "exceptId"));
		helper.append("and branchNo = ?", MapUtils.getString(params, "branchNo"));
		return super.getList(helper);
	}

	@Override
	public List<Long> findItemNum(Map<String, Object> params) {
		QueryHelper helper = new QueryHelper();
		helper.append("select count(id) from ChargeItem where 1=1");
		helper.append("and id in ?", (String[]) params.get("ids"));
		helper.append("group by item");
		return super.getList(helper);
	}

	@Override
	public Paginater findPager(Map<String, Object> params, Pager pager) {
		QueryHelper helper = new QueryHelper();
		helper.append("from ChargeItem where 1=1");
		helper.append("and way = ?", MapUtils.getString(params, "way"));
		helper.append("and item = ?", MapUtils.getString(params, "item"));
		helper.append("and branchNo = ?", MapUtils.getString(params, "branchNo"));
		return super.getPageData(helper, pager);
	}

	@Override
	protected Class getModelClass() {
		return ChargeItem.class;
	}

}
