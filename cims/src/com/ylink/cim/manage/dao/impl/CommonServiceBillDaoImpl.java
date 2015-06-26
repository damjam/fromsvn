package com.ylink.cim.manage.dao.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.MatchMode;
import org.springframework.stereotype.Repository;

import com.ylink.cim.common.state.BillState;
import com.ylink.cim.manage.dao.CommonServiceBillDao;
import com.ylink.cim.manage.dao.HouseInfoDao;
import com.ylink.cim.manage.domain.CommonServiceBill;
import com.ylink.cim.manage.domain.HouseInfo;

import flink.hibernate.BaseDaoHibernateImpl;
import flink.hibernate.QueryHelper;
import flink.util.DateUtil;
import flink.util.Pager;
import flink.util.Paginater;
import flink.util.SpringContext;
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
		if (StringUtils.isEmpty(MapUtils.getString(params, "orderType"))) {
			helper.append("order by id desc");
		}else {
			helper.orderBy(MapUtils.getString(params, "orderType"), "desc");
		}
		Paginater paginater = getPageData(helper, pager);
		Collections.sort(paginater.getList(), new java.util.Comparator() {
			HouseInfoDao houseInfoDao = (HouseInfoDao)SpringContext.getService("houseInfoDao");
			public int compare(Object o1, Object o2) {
				try {
					CommonServiceBill record1 = (CommonServiceBill) o1;
					CommonServiceBill record2 = (CommonServiceBill) o2;
					HouseInfo h1 = houseInfoDao.findById(record1.getHouseSn());
					HouseInfo h2 = houseInfoDao.findById(record2.getHouseSn());
					if (!h1.getOrderSn().equals(h2.getOrderSn())) {
						return Integer.parseInt(h1.getOrderSn()) - Integer.parseInt(h2.getOrderSn());
					}
					return 0;
				} catch (Exception e) {
					return 0;
				}
			}
		});
		return paginater;
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
		helper.append("and state = ?", MapUtils.getString(params, "state"));
		helper.append("and id = ?", MapUtils.getString(params, "id"));
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

	public List<CommonServiceBill> getAllLastBill(Map<String, Object> map) {
		QueryHelper helper = new QueryHelper();
		helper.append("from CommonServiceBill where state = ? and id in (select max(id) from CommonServiceBill group by houseSn)", BillState.PAID.getValue());
		helper.append("and id not in(select billId from BillTrack)");
		List<CommonServiceBill> list = super.getList(helper);
		return list;
	}

	
}
