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
import com.ylink.cim.manage.dao.IcDepositDao;
import com.ylink.cim.manage.domain.IcDeposit;

import flink.hibernate.BaseDaoHibernateImpl;
import flink.hibernate.QueryHelper;
import flink.util.DateUtil;
import flink.util.Pager;
import flink.util.Paginater;
@Repository("icDepositDao")
public class IcDepositDaoImpl extends BaseDaoHibernateImpl implements IcDepositDao{
	public Paginater findPager(Map<String, Object> params, Pager pager){
		QueryHelper helper = new QueryHelper();
		helper.append("from IcDeposit t where 1=1");
		if (StringUtils.isNotEmpty(MapUtils.getString(params, "startChargeDate"))) {
			helper.append("and chargeDate >= ?", DateUtil.formatDate(MapUtils.getString(params, "startChargeDate")));
		}
		if (StringUtils.isNotEmpty(MapUtils.getString(params, "endChargeDate"))) {
			helper.append("and chargeDate <= ?", DateUtil.getDayEndByYYYMMDD(MapUtils.getString(params, "endChargeDate")));
		}
		if (StringUtils.isNotEmpty(MapUtils.getString(params, "startCreateDate"))) {
			helper.append("and createDate >= ?", DateUtil.formatDate(MapUtils.getString(params, "startCreateDate")));
		}
		if (StringUtils.isNotEmpty(MapUtils.getString(params, "endCreateDate"))) {
			helper.append("and createDate <= ?", DateUtil.getDayEndByYYYMMDD(MapUtils.getString(params, "endCreateDate")));
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
			helper.append("and chargeDate >= ?", year);
			Integer yearInt = Integer.parseInt(year);
			helper.append("and chargeDate <= ?", String.valueOf(yearInt+1));
		}
	}

	@Override
	protected Class getModelClass() {
		return IcDeposit.class;
	}

	public Map<String, Object> findSumInfo(Map<String, Object> params) {
		QueryHelper helper = new QueryHelper();
		helper.append("select new map(count(id) as cnt, sum(amount) as sumAmt) from IcDeposit where 1=1");
		if (StringUtils.isNotEmpty(MapUtils.getString(params, "startDepositDate"))) {
			helper.append("and depositDate >= ?", DateUtil.formatDate(MapUtils.getString(params, "startDepositDate")));
		}
		if (StringUtils.isNotEmpty(MapUtils.getString(params, "endDepositDate"))) {
			helper.append("and depositDate <= ?", DateUtil.getDayEndByYYYMMDD(MapUtils.getString(params, "endDepositDate")));
		}
		addYearFilter(helper, MapUtils.getString(params, "year"));
		
		helper.append("and houseSn like ?", MapUtils.getString(params, "houseSn"), MatchMode.START);
		//helper.append("and state = ?", MapUtils.getString(params, "state"));
		helper.append("and id = ?", MapUtils.getString(params, "id"));
		helper.append("and state = ?", BillState.PAID.getValue());
		if (!StringUtils.equals(BranchType.HQ_0000.getValue(), MapUtils.getString(params, "branchNo"))) {
			helper.append("and branchNo = ?", MapUtils.getString(params, "branchNo"));
		}
		helper.append("group by state");
		List<Map<String, Object>> sumList = super.getList(helper);
		Map<String, Object> sumInfo = new HashMap<String, Object>();
		Long totalCnt = 0L;
		Double totalAmt = 0d;
		for (int i = 0; i < sumList.size(); i++) {
			Map<String, Object> map = sumList.get(i);
			Long cnt = (Long)map.get("cnt");
			Double sumAmt = (Double)map.get("sumAmt");
			if (cnt == null) {
				cnt = 0L;
			}
			if (sumAmt == null) {
				sumAmt = 0d;
			}
			totalCnt += cnt;
			totalAmt += sumAmt;
		}
		sumInfo.put("totalCnt", totalCnt);
		sumInfo.put("totalAmt", totalAmt);
		return sumInfo;
	}

}
