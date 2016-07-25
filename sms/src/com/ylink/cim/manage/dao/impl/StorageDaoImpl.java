package com.ylink.cim.manage.dao.impl;

import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.hibernate.criterion.MatchMode;
import org.springframework.stereotype.Component;

import com.ylink.cim.manage.dao.StorageDao;
import com.ylink.cim.manage.domain.Storage;

import flink.hibernate.BaseDaoImpl;
import flink.hibernate.QueryHelper;
import flink.util.Pager;
import flink.util.Paginater;
@Component("storageDao")
public class StorageDaoImpl extends BaseDaoImpl implements StorageDao {

	@Override
	protected Class getModelClass() {
		return Storage.class;
	}

	@Override
	public Paginater findPager(Map<String, Object> params, Pager pager) {
		QueryHelper helper = new QueryHelper();
		helper.append("from Storage where 1=1");
		helper.append("and shelf like ?", MapUtils.getString(params, "shelf"), MatchMode.ANYWHERE);
		helper.append("and product like ?", MapUtils.getString(params, "product"), MatchMode.ANYWHERE);
		helper.append("and productType = ?", MapUtils.getString(params, "productType"));
		helper.append("and color = ?", MapUtils.getString(params, "color"));
		helper.append("and material = ?", MapUtils.getString(params, "material"));
		helper.append("and pattern = ?", MapUtils.getString(params, "pattern"));
		helper.append("and carModel like ?", MapUtils.getString(params, "carModel"), MatchMode.ANYWHERE);
		helper.append("order by id desc");
		return super.getPageData(helper, pager);
	}

	

}
