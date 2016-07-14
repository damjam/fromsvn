package com.ylink.cim.manage.dao.impl;

import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.hibernate.criterion.MatchMode;
import org.springframework.stereotype.Component;

import com.ylink.cim.manage.dao.RentRecordDao;
import com.ylink.cim.manage.domain.RentRecord;

import flink.hibernate.BaseDaoImpl;
import flink.hibernate.QueryHelper;
import flink.util.Pager;
import flink.util.Paginater;
@Component("rentRecordDao")
public class RentRecordDaoImpl extends BaseDaoImpl implements RentRecordDao {

	@Override
	protected Class getModelClass() {
		return RentRecord.class;
	}
	
	@Override
	public Paginater findPaginater(Map<String, Object> params, Pager pager) {
		QueryHelper helper = new QueryHelper();
		helper.append("from RentRecord where 1=1");
		helper.append("and houseSn = ?", MapUtils.getString(params, "houseSn"));
		helper.append("and renterName like ?", MapUtils.getString(params, "renterName"), MatchMode.ANYWHERE);
		return super.getPageData(helper, pager);
	}
	
}
