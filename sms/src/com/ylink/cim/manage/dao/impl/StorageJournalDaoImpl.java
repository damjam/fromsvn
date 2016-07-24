package com.ylink.cim.manage.dao.impl;

import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.hibernate.criterion.MatchMode;
import org.springframework.stereotype.Component;

import com.ylink.cim.manage.dao.StorageJournalDao;
import com.ylink.cim.manage.domain.StorageJournal;

import flink.hibernate.BaseDaoImpl;
import flink.hibernate.QueryHelper;
import flink.util.Pager;
import flink.util.Paginater;
@Component("storageJournalDao")
public class StorageJournalDaoImpl extends BaseDaoImpl implements StorageJournalDao {

	@Override
	protected Class getModelClass() {
		return StorageJournal.class;
	}

	@Override
	public Paginater findPager(Map<String, Object> params, Pager pager) {
		QueryHelper helper = new QueryHelper();
		helper.append("from StorageJournal where 1=1");
		helper.append("and shelf like ?", MapUtils.getString(params, "shelf"), MatchMode.ANYWHERE);
		helper.append("and product like ?", MapUtils.getString(params, "product"), MatchMode.ANYWHERE);
		helper.append("and productType = ?", MapUtils.getString(params, "productType"));
		helper.append("and color = ?", MapUtils.getString(params, "color"));
		helper.append("and material = ?", MapUtils.getString(params, "material"));
		helper.append("and pattern = ?", MapUtils.getString(params, "pattern"));
		helper.append("and code = ?", MapUtils.getString(params, "code"));
		helper.append("and carModel like ?", MapUtils.getString(params, "carModel"), MatchMode.ANYWHERE);
		return super.getPageData(helper, pager);
	}

}
