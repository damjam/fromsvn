package com.ylink.cim.manage.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.ylink.cim.manage.dao.AdRentBillDao;
import com.ylink.cim.manage.domain.AdRent;

import flink.hibernate.BaseDaoHibernateImpl;
import flink.hibernate.QueryHelper;
import flink.util.DateUtil;
import flink.util.Pager;
import flink.util.Paginater;
@Repository("adRentBillDao")
public class AdRentBillDaoImpl extends BaseDaoHibernateImpl implements AdRentBillDao {

	public Paginater findBillPager(Map<String, Object> params, Pager pager) {
		QueryHelper helper = new QueryHelper();
		helper.append("from AdRent t where 1=1");
		if (StringUtils.isNotEmpty(MapUtils.getString(params, "startCreateDate"))) {
			helper.append("and createDate >= ?", DateUtil.formatDate(MapUtils.getString(params, "startCreateDate")));
		}
		if (StringUtils.isNotEmpty(MapUtils.getString(params, "endCreateDate"))) {
			helper.append("and createDate <= ?", DateUtil.getDayEndByYYYMMDD(MapUtils.getString(params, "endCreateDate")));
		}
		helper.append("and branchNo = ?", MapUtils.getString(params, "branchNo"));
		helper.append("order by t.createDate desc");
		return super.getPageData(helper, pager);
	}

	public List<AdRent> findBills(Map<String, Object> params) {
		return null;
	}

	public Map<String, Object> findSumInfo(Map<String, Object> map) {
		return null;
	}

	@Override
	protected Class getModelClass() {
		return AdRent.class;
	}

}
