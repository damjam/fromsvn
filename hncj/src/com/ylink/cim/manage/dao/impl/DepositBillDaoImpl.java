package com.ylink.cim.manage.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.MatchMode;
import org.springframework.stereotype.Repository;

import com.ylink.cim.common.state.BillState;
import com.ylink.cim.common.type.BranchType;
import com.ylink.cim.manage.dao.DepositBillDao;
import com.ylink.cim.manage.domain.DepositBill;

import flink.hibernate.BaseDaoImpl;
import flink.hibernate.QueryHelper;
import flink.util.DateUtil;
import flink.util.Pager;
import flink.util.Paginater;
@Repository("depositBillDao")
public class DepositBillDaoImpl extends BaseDaoImpl implements DepositBillDao{
	public Paginater findPager(Map<String, Object> params, Pager pager){
		QueryHelper helper = new QueryHelper();
		helper.append("from DepositBill t where 1=1");
		if (StringUtils.isNotEmpty(MapUtils.getString(params, "startDepositDate"))) {
			helper.append("and depositDate >= ?", DateUtil.formatDate(MapUtils.getString(params, "startDepositDate")));
		}
		if (StringUtils.isNotEmpty(MapUtils.getString(params, "endDepositDate"))) {
			helper.append("and depositDate <= ?", DateUtil.getDayEndByYYYMMDD(MapUtils.getString(params, "endDepositDate")));
		}
		if (StringUtils.isNotEmpty(MapUtils.getString(params, "startRefundDate"))) {
			helper.append("and refundDate >= ?", DateUtil.formatDate(MapUtils.getString(params, "startRefundDate")));
		}
		if (StringUtils.isNotEmpty(MapUtils.getString(params, "endRefundDate"))) {
			helper.append("and refundDate <= ?", DateUtil.getDayEndByYYYMMDD(MapUtils.getString(params, "endRefundDate")));
		}
		addYearFilter(helper, MapUtils.getString(params, "year"));
		helper.append("and houseSn like ?", MapUtils.getString(params, "houseSn"), MatchMode.START);
		helper.append("and state = ?", MapUtils.getString(params, "state"));
		helper.append("and id = ?", MapUtils.getString(params, "id"));
		if (!StringUtils.equals(BranchType.HQ_0000.getValue(), MapUtils.getString(params, "branchNo"))) {
			helper.append("and branchNo = ?", MapUtils.getString(params, "branchNo"));
		}
		helper.append("order by t.id desc");
		return super.getPageData(helper, pager);
	}

	private void addYearFilter(QueryHelper helper, String year) {
		if (!StringUtils.isBlank(year)) {
			helper.append("and depositDate >= ?", year);
			Integer yearInt = Integer.parseInt(year);
			helper.append("and depositDate <= ?", String.valueOf(yearInt+1));
		}
	}

	@Override
	protected Class getModelClass() {
		return DepositBill.class;
	}

	public Map<String, Object> findSumInfo(Map<String, Object> params) {
		QueryHelper helper = new QueryHelper();
		helper.append("select new map(count(t.id) as cnt, sum(t.amount) as sumAmt, sum(t.refundAmount) as sumRefundAmt, t.state as state) from DepositBill t where 1=1");
		if (StringUtils.isNotEmpty(MapUtils.getString(params, "startDepositDate"))) {
			helper.append("and depositDate >= ?", DateUtil.formatDate(MapUtils.getString(params, "startDepositDate")));
		}
		if (StringUtils.isNotEmpty(MapUtils.getString(params, "endDepositDate"))) {
			helper.append("and depositDate <= ?", DateUtil.getDayEndByYYYMMDD(MapUtils.getString(params, "endDepositDate")));
		}
		addYearFilter(helper, MapUtils.getString(params, "year"));
		if (StringUtils.isNotEmpty(MapUtils.getString(params, "startRefundDate"))) {
			helper.append("and refundDate >= ?", DateUtil.formatDate(MapUtils.getString(params, "startRefundDate")));
		}
		if (StringUtils.isNotEmpty(MapUtils.getString(params, "endRefundDate"))) {
			helper.append("and refundDate <= ?", DateUtil.getDayEndByYYYMMDD(MapUtils.getString(params, "endRefundDate")));
		}
		helper.append("and houseSn like ?", MapUtils.getString(params, "houseSn"), MatchMode.START);
		//helper.append("and state = ?", MapUtils.getString(params, "state"));
		helper.append("and id = ?", MapUtils.getString(params, "id"));
		if (!StringUtils.equals(BranchType.HQ_0000.getValue(), MapUtils.getString(params, "branchNo"))) {
			helper.append("and branchNo = ?", MapUtils.getString(params, "branchNo"));
		}
		helper.append("group by t.state");
		List<Map<String, Object>> sumList = super.getList(helper);
		Map<String, Object> sumInfo = new HashMap<String, Object>();
		Long totalCnt = 0L;
		Double totalAmt = 0d;
		Long paidCnt = 0L;
		Double paidAmt = 0d;
		Long refundCnt = 0L;
		Double refundAmt = 0d;
		for (int i = 0; i < sumList.size(); i++) {
			Map<String, Object> map = sumList.get(i);
			String state = (String)map.get("state");
			Long cnt = (Long)map.get("cnt");
			Double sumAmt = (Double)map.get("sumAmt");
			if (cnt == null) {
				cnt = 0L;
			}
			if (sumAmt == null) {
				sumAmt = 0d;
			}
			if (BillState.PAID.getValue().equals(state)) {
				paidCnt = cnt;
				paidAmt = sumAmt;
			}else if (BillState.REFUND.getValue().equals(state)) {
				refundCnt = cnt;
				refundAmt = sumAmt;
			}
			totalCnt += cnt;
			totalAmt += sumAmt;
		}
		sumInfo.put("paidCnt", paidCnt);
		sumInfo.put("paidAmt", paidAmt);
		sumInfo.put("refundCnt", refundCnt);
		sumInfo.put("refundAmt", refundAmt);
		sumInfo.put("totalCnt", totalCnt);
		sumInfo.put("totalAmt", totalAmt);
		return sumInfo;
	}

}
