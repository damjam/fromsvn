package com.ylink.cim.manage.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.ylink.cim.common.type.BranchType;
import com.ylink.cim.manage.dao.ChargeItemDao;
import com.ylink.cim.manage.domain.ChargeItem;

import flink.hibernate.BaseDaoImpl;
import flink.hibernate.QueryHelper;
import flink.util.Pager;
import flink.util.Paginater;

@Component("chargeItemDao")
public class ChargeItemDaoImpl extends BaseDaoImpl implements ChargeItemDao {

	public List<ChargeItem> findBy(Map<String, Object> params) {
		QueryHelper helper = new QueryHelper();
		helper.append("from ChargeItem where 1=1");
		helper.append("and way = ?", MapUtils.getString(params, "way"));
		helper.append("and item = ?", MapUtils.getString(params, "item"));
		helper.append("and itemName = ?", MapUtils.getString(params, "itemName"));
		helper.append("and id <> ?", MapUtils.getString(params, "exceptId"));
		if (!StringUtils.equals(BranchType.HQ_0000.getValue(), MapUtils.getString(params, "branchNo"))) {
			helper.append("and branchNo = ?", MapUtils.getString(params, "branchNo"));
		}
		return super.getList(helper);
	}

	public List<Long> findItemNum(Map<String, Object> params) {
		QueryHelper helper = new QueryHelper();
		helper.append("select count(id) from ChargeItem where 1=1");
		helper.append("and id in ?", (String[]) params.get("ids"));
		helper.append("group by item");
		return super.getList(helper);
	}

	public Paginater findPager(Map<String, Object> params, Pager pager) {
		QueryHelper helper = new QueryHelper();
		helper.append("from ChargeItem where 1=1");
		helper.append("and way = ?", MapUtils.getString(params, "way"));
		helper.append("and item = ?", MapUtils.getString(params, "item"));
		if (!StringUtils.equals(BranchType.HQ_0000.getValue(), MapUtils.getString(params, "branchNo"))) {
			helper.append("and branchNo = ?", MapUtils.getString(params, "branchNo"));
		}
		return super.getPageData(helper, pager);
	}

	@Override
	protected Class getModelClass() {
		// TODO Auto-generated method stub
		return ChargeItem.class;
	}

}
