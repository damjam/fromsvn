package com.ylink.cim.manage.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.MatchMode;
import org.springframework.stereotype.Repository;

import com.ylink.cim.common.state.BillState;
import com.ylink.cim.manage.dao.CommonServiceBillDao;
import com.ylink.cim.manage.domain.CommonServiceBill;

import flink.hibernate.BaseDaoHibernateImpl;
import flink.hibernate.QueryHelper;
import flink.util.DateUtil;
import flink.util.Pager;
import flink.util.Paginater;
@Repository("commonServiceBillDao")
public class CommonServiceBillDaoImpl extends BaseDaoHibernateImpl implements CommonServiceBillDao{
	public Paginater findPager(Map<String, Object> params, Pager pager){
		QueryHelper helper = new QueryHelper();
		helper.append("from CommonServiceBill t where 1=1");
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
		return CommonServiceBill.class;
	}

	public Map<String, Object> findSumInfo(Map<String, Object> params) {
		QueryHelper helper = new QueryHelper();
		helper.append("select new map(count(t.id) as cnt, sum(t.totalAmount) as sumAmt, t.state as state) from CommonServiceBill t where 1=1");
		if (StringUtils.isNotEmpty(MapUtils.getString(params, "startChargeDate"))) {
			helper.append("and chargeDate >= ?", DateUtil.formatDate(MapUtils.getString(params, "startChargeDate")));
		}
		if (StringUtils.isNotEmpty(MapUtils.getString(params, "endChargeDate"))) {
			helper.append("and chargeDate <= ?", DateUtil.getDayEndByYYYMMDD(MapUtils.getString(params, "endChargeDate")));
		}
		addYearFilter(helper, MapUtils.getString(params, "year"));
		helper.append("and houseSn like ?", MapUtils.getString(params, "houseSn"), MatchMode.START);
		//helper.append("and state = ?", MapUtils.getString(params, "state"));
		helper.append("and id = ?", MapUtils.getString(params, "id"));
		helper.append("and branchNo = ?", MapUtils.getString(params, "branchNo"));
		helper.append("group by t.state");
		List<Map<String, Object>> sumList = super.getList(helper);
		Map<String, Object> sumInfo = new HashMap<String, Object>();
		Long totalCnt = 0L;
		Double totalAmt = 0d;
		Long paidCnt = 0L;
		Double paidAmt = 0d;
		Long unpayCnt = 0L;
		Double unpayAmt = 0d;
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
			}else if (BillState.UNPAY.getValue().equals(state)) {
				unpayCnt = cnt;
				unpayAmt = sumAmt;
			}else if (BillState.REVERSE.getValue().equals(state)) {
				continue;
			}
			totalCnt += cnt;
			totalAmt += sumAmt;
		}
		sumInfo.put("paidCnt", paidCnt);
		sumInfo.put("paidAmt", paidAmt);
		sumInfo.put("unpayCnt", unpayCnt);
		sumInfo.put("unpayAmt", unpayAmt);
		sumInfo.put("totalCnt", totalCnt);
		sumInfo.put("totalAmt", totalAmt);
		return sumInfo;
	}

	public CommonServiceBill getLastBill(Map<String, Object> map) {
		QueryHelper helper = new QueryHelper();
		helper.append("from CommonServiceBill where state = ?", BillState.PAID.getValue());
		helper.append("and houseSn = ?", MapUtils.getString(map, "houseSn"));
		helper.append("order by id desc");
		List<CommonServiceBill> list = super.getList(helper);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	private void addYearFilter(QueryHelper helper, String year) {
		if (!StringUtils.isBlank(year)) {
			helper.append("and createDate >= ?", year);
			Integer yearInt = Integer.parseInt(year);
			helper.append("and createDate <= ?", String.valueOf(yearInt+1));
		}
	}

	
}
