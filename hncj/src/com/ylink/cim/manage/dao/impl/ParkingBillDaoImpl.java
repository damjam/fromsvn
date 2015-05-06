package com.ylink.cim.manage.dao.impl;

import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.MatchMode;
import org.springframework.stereotype.Repository;

import com.ylink.cim.common.state.BillState;
import com.ylink.cim.common.type.BranchType;
import com.ylink.cim.manage.dao.ParkingBillDao;
import com.ylink.cim.manage.domain.ParkingBill;

import flink.hibernate.BaseDaoHibernateImpl;
import flink.hibernate.QueryHelper;
import flink.util.Pager;
import flink.util.Paginater;
@Repository("parkingBillDao")
public class ParkingBillDaoImpl extends BaseDaoHibernateImpl implements ParkingBillDao{

	private void addYearFilter(QueryHelper helper, String year) {
		if (!StringUtils.isBlank(year)) {
			helper.append("and createDate >= ?", year);
			Integer yearInt = Integer.parseInt(year);
			helper.append("and createDate <= ?", String.valueOf(yearInt+1));
		}
	}

	public Paginater findPager(Map<String, Object> params, Pager pager) {
		QueryHelper helper = new QueryHelper();
		helper.append("from ParkingBill t where 1=1");
		helper.append("and recordMonth >= ?", MapUtils.getString(params, "startRecordMonth"));
		helper.append("and recordMonth <= ?", MapUtils.getString(params, "endRecordMonth"));
		addYearFilter(helper, MapUtils.getString(params, "year"));
		helper.append("and houseSn like ?", MapUtils.getString(params, "houseSn"), MatchMode.START);
		helper.append("and parkingSn = ?", MapUtils.getString(params, "parkingSn"));
		helper.append("and carSn = ?", MapUtils.getString(params, "carSn"));
		helper.append("and state = ?", MapUtils.getString(params, "state"));
		helper.append("and id = ?", MapUtils.getString(params, "id"));
		if (!StringUtils.equals(BranchType.HQ_0000.getValue(), MapUtils.getString(params, "branchNo"))) {
			helper.append("and branchNo = ?", MapUtils.getString(params, "branchNo"));
		}
		helper.append("order by t.createDate desc");
		return super.getPageData(helper, pager);
	}

	public Map<String, Object> findSumInfo(Map<String, Object> params) {
		QueryHelper helper = new QueryHelper();
		helper.append("select new map(count(t.id) as cnt, sum(t.amount) as amt) from ParkingBill t where 1=1");
		helper.append("and recordMonth >= ?", MapUtils.getString(params, "startRecordMonth"));
		helper.append("and recordMonth <= ?", MapUtils.getString(params, "endRecordMonth"));
		addYearFilter(helper, MapUtils.getString(params, "year"));
		helper.append("and houseSn like ?", MapUtils.getString(params, "houseSn"), MatchMode.START);
		helper.append("and parkingSn = ?", MapUtils.getString(params, "parkingSn"));
		helper.append("and carSn = ?", MapUtils.getString(params, "carSn"));
		helper.append("and state = ?", BillState.PAID.getValue());
		helper.append("and id = ?", MapUtils.getString(params, "id"));
		helper.append("and branchNo = ?", MapUtils.getString(params, "branchNo"));
		Map<String, Object> sumInfo = (Map<String, Object>)super.getUniqueResult(helper);
		if (sumInfo.get("amt") == null) {
			sumInfo.put("amt", 0d);
		}
		return sumInfo;
	}

	@Override
	protected Class getModelClass() {
		return ParkingBill.class;
	}

}
