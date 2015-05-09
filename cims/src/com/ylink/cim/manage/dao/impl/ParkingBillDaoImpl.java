package com.ylink.cim.manage.dao.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.MatchMode;
import org.springframework.stereotype.Repository;

import com.ylink.cim.common.state.BillState;
import com.ylink.cim.manage.dao.HouseInfoDao;
import com.ylink.cim.manage.dao.ParkingBillDao;
import com.ylink.cim.manage.domain.CommonServiceBill;
import com.ylink.cim.manage.domain.HouseInfo;
import com.ylink.cim.manage.domain.ParkingBill;
import com.ylink.cim.manage.domain.WaterRecord;

import flink.hibernate.BaseDaoHibernateImpl;
import flink.hibernate.QueryHelper;
import flink.util.Pager;
import flink.util.Paginater;
import flink.util.SpringContext;
@Repository("parkingBillDao")
public class ParkingBillDaoImpl extends BaseDaoHibernateImpl implements ParkingBillDao{

	@Override
	protected Class getModelClass() {
		return ParkingBill.class;
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
		helper.orderBy(MapUtils.getString(params, "orderType"), "desc");
		Paginater paginater = getPageData(helper, pager);
		Collections.sort(paginater.getList(), new java.util.Comparator() {
			HouseInfoDao houseInfoDao = (HouseInfoDao)SpringContext.getService("houseInfoDao");
			public int compare(Object o1, Object o2) {
				try {
					WaterRecord record1 = (WaterRecord) o1;
					WaterRecord record2 = (WaterRecord) o2;
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

	private void addYearFilter(QueryHelper helper, String year) {
		if (!StringUtils.isBlank(year)) {
			helper.append("and createDate >= ?", year);
			Integer yearInt = Integer.parseInt(year);
			helper.append("and createDate <= ?", String.valueOf(yearInt+1));
		}
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
		Map<String, Object> sumInfo = (Map<String, Object>)super.getUniqueResult(helper);
		if (sumInfo.get("amt") == null) {
			sumInfo.put("amt", 0d);
		}
		return sumInfo;
	}

	public List<ParkingBill> getAllLastBill(Map<String, Object> map) {
		QueryHelper helper = new QueryHelper();
		helper.append("from ParkingBill where state = ? and id in (select max(id) from ParkingBill group by houseSn)", BillState.PAID.getValue());
		helper.append("and id not in(select billId from BillTrack)");
		return super.getList(helper);
	}

}
