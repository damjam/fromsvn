package com.ylink.cim.manage.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Component;

import com.ylink.cim.manage.dao.ChargeParamItemDao;
import com.ylink.cim.manage.domain.ChargeParamItem;

import flink.hibernate.BaseDaoImpl;
import flink.hibernate.QueryHelper;
import flink.util.Pager;
import flink.util.Paginater;

@Component("chargeParamItemDao")
public class ChargeParamItemDaoImpl extends BaseDaoImpl implements
		ChargeParamItemDao {

	@Override
	public Paginater findPager(Map<String, Object> params, Pager pager) {
		QueryHelper helper = new QueryHelper();
		helper.append("from ChargeParamItem where 1=1");
		helper.append("and id.paramId = ?",
				MapUtils.getString(params, "paramId"));
		return super.getPageData(helper, pager);
	}

	@Override
	public List<ChargeParamItem> findBy(Map<String, Object> params) {
		QueryHelper helper = new QueryHelper();
		helper.append("from ChargeParamItem where 1=1");
		helper.append("and id.paramId = ?",
				MapUtils.getString(params, "paramId"));
		helper.append("and id.itemId = ?", MapUtils.getString(params, "itemId"));
		return super.getList(helper);
	}

	@Override
	protected Class getModelClass() {
		return ChargeParamItem.class;
	}

	@Override
	public void deleteByParamId(String id) {
		QueryHelper helper = new QueryHelper();
		helper.append("delete ChargeParamItem where id.paramId = ?", id);
		super.execute(helper);
	}

}
