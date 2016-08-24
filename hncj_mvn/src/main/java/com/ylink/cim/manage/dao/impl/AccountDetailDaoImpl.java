package com.ylink.cim.manage.dao.impl;

import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.ylink.cim.manage.dao.AccountDetailDao;
import com.ylink.cim.manage.domain.AccountDetail;

import flink.hibernate.BaseDaoImpl;
import flink.hibernate.QueryHelper;
import flink.util.DateUtil;
import flink.util.Pager;
import flink.util.Paginater;

@Repository("accountDetailDao")
public class AccountDetailDaoImpl extends BaseDaoImpl implements AccountDetailDao {

	private void addYearFilter(QueryHelper helper, String year) {
		if (!StringUtils.isBlank(year)) {
			helper.append("and createDate >= ?", year);
			Integer yearInt = Integer.parseInt(year);
			helper.append("and createDate <= ?", String.valueOf(yearInt + 1));
		}
	}

	@Override
	public Paginater findPager(Map<String, Object> params, Pager pager) {
		QueryHelper helper = new QueryHelper();
		helper.append("from AccountDetail where 1=1");
		helper.append("and acctNo = ?", MapUtils.getString(params, "acctNo"));
		helper.append("and type = ?", MapUtils.getString(params, "type"));
		helper.append("and inoutType = ?", MapUtils.getString(params, "inoutType"));
		if (StringUtils.isNotEmpty(MapUtils.getString(params, "startCreateDate"))) {
			helper.append("and createDate >= ?", DateUtil.formatDate(MapUtils.getString(params, "startCreateDate")));
		}
		if (StringUtils.isNotEmpty(MapUtils.getString(params, "endCreateDate"))) {
			helper.append("and createDate <= ?",
					DateUtil.getDayEndByYYYMMDD(MapUtils.getString(params, "endCreateDate")));
		}
		addYearFilter(helper, MapUtils.getString(params, "year"));
		helper.append("order by id desc");
		return super.getPageData(helper, pager);
	}

	@Override
	protected Class getModelClass() {
		return AccountDetail.class;
	}

}
