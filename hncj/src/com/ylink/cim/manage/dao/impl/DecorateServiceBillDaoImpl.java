package com.ylink.cim.manage.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.MatchMode;
import org.springframework.stereotype.Repository;

import com.ylink.cim.common.state.BillState;
import com.ylink.cim.manage.dao.DecorateServiceBillDao;
import com.ylink.cim.manage.domain.DecorateServiceBill;

import flink.hibernate.BaseDaoImpl;
import flink.hibernate.QueryHelper;
import flink.util.DateUtil;
import flink.util.Pager;
import flink.util.Paginater;
@Repository("decorateServiceBillDao")
public class DecorateServiceBillDaoImpl extends BaseDaoImpl implements DecorateServiceBillDao{
	@Override
	public Paginater findPager(Map<String, Object> params, Pager pager){
		QueryHelper helper = new QueryHelper();
		helper.append("from DecorateServiceBill t where 1=1");
		if (StringUtils.isNotEmpty(MapUtils.getString(params, "startChargeDate"))) {
			helper.append("and chargeDate >= ?", DateUtil.formatDate(MapUtils.getString(params, "startChargeDate")));
		}
		if (StringUtils.isNotEmpty(MapUtils.getString(params, "endChargeDate"))) {
			helper.append("and chargeDate <= ?", DateUtil.getDayEndByYYYMMDD(MapUtils.getString(params, "endChargeDate")));
		}
		addYearFilter(helper, MapUtils.getString(params, "year"));
		helper.append("and houseSn like ?", MapUtils.getString(params, "houseSn"), MatchMode.START);
		helper.append("and state = ?", MapUtils.getString(params, "state"));
		helper.append("and id = ?", MapUtils.getString(params, "id"));
		helper.append("and branchNo = ?", MapUtils.getString(params, "branchNo"));
		helper.append("order by t.id desc");
		return super.getPageData(helper, pager);
	}

	@Override
	protected Class getModelClass() {
		return DecorateServiceBill.class;
	}

	@Override
	public Map<String, Object> findSumInfo(Map<String, Object> params) {
		QueryHelper helper = new QueryHelper();
		helper.append("select new map(count(t.id) as cnt, sum(t.amount) as sumAmt, sum(t.paidAmount) as sumPaidAmt, sum(t.liftFee) as liftAmt, sum(t.cleanAmount) as cleanAmt) from DecorateServiceBill t where 1=1");
		if (StringUtils.isNotEmpty(MapUtils.getString(params, "startChargeDate"))) {
			helper.append("and chargeDate >= ?", DateUtil.formatDate(MapUtils.getString(params, "startChargeDate")));
		}
		if (StringUtils.isNotEmpty(MapUtils.getString(params, "endChargeDate"))) {
			helper.append("and chargeDate <= ?", DateUtil.getDayEndByYYYMMDD(MapUtils.getString(params, "endChargeDate")));
		}
		addYearFilter(helper, MapUtils.getString(params, "year"));
		helper.append("and houseSn like ?", MapUtils.getString(params, "houseSn"), MatchMode.START);
		helper.append("and state = ?", BillState.PAID.getValue());
		helper.append("and id = ?", MapUtils.getString(params, "id"));
		helper.append("and branchNo = ?", MapUtils.getString(params, "branchNo"));
		Map<String, Object> sumInfo = (Map<String, Object>)super.getUniqueResult(helper);
		if (sumInfo.get("sumAmt") == null) {
			sumInfo.put("sumAmt", 0d);
		}
		if (sumInfo.get("sumPaidAmt") == null) {
			sumInfo.put("sumPaidAmt", 0d);
		}
		if (sumInfo.get("liftAmt") == null) {
			sumInfo.put("liftAmt", 0d);
		}
		if (sumInfo.get("cleanAmt") == null) {
			sumInfo.put("cleanAmt", 0d);
		}
		return sumInfo;
	}

	private void addYearFilter(QueryHelper helper, String year) {
		if (!StringUtils.isBlank(year)) {
			helper.append("and chargeDate >= ?", year);
			Integer yearInt = Integer.parseInt(year);
			helper.append("and chargeDate <= ?", String.valueOf(yearInt+1));
		}
	}

	public List<DecorateServiceBill> findList(Map<String, Object> params) {
		QueryHelper helper = new QueryHelper();
		helper.append("from DecorateServiceBill where 1=1");
		helper.append("and houseSn = ?", MapUtils.getString(params, "houseSn"));
		helper.append("and amount = ?", MapUtils.getDouble(params, "amount"));
		helper.append("and branchNo = ?", MapUtils.getString(params, "branchNo"));
		return super.getList(helper);
	}
}
