package com.ylink.cim.manage.dao.impl;

import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.ylink.cim.common.type.BranchType;
import com.ylink.cim.manage.dao.HouseRentBillDao;
import com.ylink.cim.manage.domain.HouseRentBill;

import flink.hibernate.BaseDaoImpl;
import flink.hibernate.QueryHelper;
import flink.util.DateUtil;
import flink.util.Pager;
import flink.util.Paginater;
@Component("houseRentBillDao")
public class HouseRentBillDaoImpl extends BaseDaoImpl implements HouseRentBillDao {

	@Override
	protected Class getModelClass() {
		return HouseRentBill.class;
	}

	@Override
	public Paginater findPager(Map<String, Object> map, Pager pager) {
		QueryHelper helper = new QueryHelper();
		helper.append("from HouseRentBill where 1=1");
		helper.append("and merchantNo = ?", MapUtils.getString(map, "merchantNo"));
		helper.append("and tel = ?", MapUtils.getString(map, "tel"));
		helper.append("and branchNo = ?",MapUtils.getString(map, "branchNo"));
		helper.append("and state = ?", MapUtils.getString(map, "state"));
		helper.append("and id = ?", MapUtils.getString(map, "id"));
		if (StringUtils.isNotEmpty(MapUtils.getString(map, "startChargeDate"))) {
			helper.append("and chargeDate >= ?", DateUtil.formatDate(MapUtils.getString(map, "startChargeDate")));
		}
		if (StringUtils.isNotEmpty(MapUtils.getString(map, "endChargeDate"))) {
			helper.append("and chargeDate <= ?", DateUtil.getDayEndByYYYMMDD(MapUtils.getString(map, "endChargeDate")));
		}
		helper.append("order by id desc");
		return super.getPageData(helper, pager);
	}

	@Override
	public Map<String, Object> findSumInfo(Map<String, Object> params) {
		QueryHelper helper = new QueryHelper();
		helper.append("select new map(count(t.id) as totalCnt, sum(t.paidAmt) as totalAmt) from HouseRentBill t where 1=1");
		if (StringUtils.isNotEmpty(MapUtils.getString(params, "startChargeDate"))) {
			helper.append("and chargeDate >= ?", DateUtil.formatDate(MapUtils.getString(params, "startChargeDate")));
		}
		if (StringUtils.isNotEmpty(MapUtils.getString(params, "endChargeDate"))) {
			helper.append("and chargeDate <= ?", DateUtil.getDayEndByYYYMMDD(MapUtils.getString(params, "endChargeDate")));
		}
		if (!StringUtils.equals(BranchType.HQ_0000.getValue(), MapUtils.getString(params, "branchNo"))) {
			helper.append("and branchNo = ?", MapUtils.getString(params, "branchNo"));
		}
		helper.append("and state = ?", MapUtils.getString(params, "state"));
		helper.append("and id = ?", MapUtils.getString(params, "id"));
		Map<String, Object> sumInfo = (Map<String, Object>)super.getUniqueResult(helper);
		if (sumInfo.get("totalAmt") == null) {
			sumInfo.put("totalAmt", 0d);
		}
		return sumInfo;
	}

}
