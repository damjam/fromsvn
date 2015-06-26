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
import com.ylink.cim.common.type.BranchType;
import com.ylink.cim.manage.dao.HouseInfoDao;
import com.ylink.cim.manage.dao.WaterBillDao;
import com.ylink.cim.manage.domain.AccountDetail;
import com.ylink.cim.manage.domain.HouseInfo;
import com.ylink.cim.manage.domain.WaterBill;

import flink.hibernate.BaseDaoHibernateImpl;
import flink.hibernate.QueryHelper;
import flink.util.Pager;
import flink.util.Paginater;
import flink.util.SpringContext;
@Repository("waterBillDao")
public class WaterBillDaoImpl extends BaseDaoHibernateImpl implements WaterBillDao{
	private void addYearFilter(QueryHelper helper, String year) {
		if (!StringUtils.isBlank(year)) {
			helper.append("and createDate >= ?", year);
			Integer yearInt = Integer.parseInt(year);
			helper.append("and createDate <= ?", String.valueOf(yearInt+1));
		}
	}

	public List<WaterBill> findBills(Map<String, Object> params) {
		QueryHelper helper = new QueryHelper();
		helper.append("from WaterBill where 1=1");
		helper.append("and houseSn = ?", MapUtils.getString(params, "houseSn"));
		helper.append("and state in ?", (String[])params.get("states"));
		if (!StringUtils.equals(BranchType.HQ_0000.getValue(), MapUtils.getString(params, "branchNo"))) {
			helper.append("and branchNo = ?", MapUtils.getString(params, "branchNo"));
		}
		helper.append("order by id");
		return super.getList(helper);
	}

	public List findErr() {
		QueryHelper helper = new QueryHelper();
		helper.append("select new map(w.id as id) from WaterBill w, WaterBill w2 where 1=1 and w.recordMonth = w2.recordMonth and w.id>w2.id and w.houseSn=w2.houseSn");
		return super.getList(helper);
	}

	public Map<String, Object> findSumInfo(Map<String, Object> params) {
		QueryHelper helper = new QueryHelper();
		helper.append("select new map(count(t.id) as cnt, sum(t.amount) as sumAmt, t.state as state) from WaterBill t where 1=1");
		helper.append("and recordMonth >= ?", MapUtils.getString(params, "startRecordMonth"));
		helper.append("and recordMonth <= ?", MapUtils.getString(params, "endRecordMonth"));
		helper.append("and createDate >= ?", MapUtils.getString(params, "startCreateDate"));
		helper.append("and createDate <= ?", MapUtils.getString(params, "endCreateDate"));
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
		Long partPaidCnt = 0L;
		Double partPaidAmt = 0d;
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
			}else if (BillState.PART_PAID.getValue().equals(state)) {
				partPaidCnt = cnt;
				partPaidAmt = sumAmt;
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
		sumInfo.put("partPaidCnt", partPaidCnt);
		sumInfo.put("partPaidAmt", partPaidAmt);
		sumInfo.put("unpayCnt", unpayCnt);
		sumInfo.put("unpayAmt", unpayAmt);
		sumInfo.put("totalCnt", totalCnt);
		sumInfo.put("totalAmt", totalAmt);
		return sumInfo;
	}

	public Paginater findWaterBillPager(Map<String, Object> params, Pager pager){
		QueryHelper helper = new QueryHelper();
		helper.append("from WaterBill t where 1=1");
		helper.append("and recordMonth >= ?", MapUtils.getString(params, "startRecordMonth"));
		helper.append("and recordMonth <= ?", MapUtils.getString(params, "endRecordMonth"));
		helper.append("and createDate >= ?", MapUtils.getString(params, "startCreateDate"));
		helper.append("and createDate <= ?", MapUtils.getString(params, "endCreateDate"));
		addYearFilter(helper, MapUtils.getString(params, "year"));
		helper.append("and houseSn like ?", MapUtils.getString(params, "houseSn"), MatchMode.START);
		helper.append("and state = ?", MapUtils.getString(params, "state"));
		helper.append("and id = ?", MapUtils.getString(params, "id"));
		if (!StringUtils.equals(BranchType.HQ_0000.getValue(), MapUtils.getString(params, "branchNo"))) {
			helper.append("and branchNo = ?", MapUtils.getString(params, "branchNo"));
		}
		helper.append("order by t.createDate desc");
		Paginater paginater = super.getPageData(helper, pager);
		Collections.sort(paginater.getList(), new java.util.Comparator() {
			HouseInfoDao houseInfoDao = (HouseInfoDao)SpringContext.getService("houseInfoDao");
			public int compare(Object o1, Object o2) {
				try {
					WaterBill record1 = (WaterBill) o1;
					WaterBill record2 = (WaterBill) o2;
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
		return WaterBill.class;
	}

	public AccountDetail getstDeta(String id) {
		QueryHelper helper = new QueryHelper();
		helper.append("from AccountDetail t where t.acctNo=? ", id);
		helper.append("order by t.id desc");
		return (AccountDetail)super.getList(helper).get(0);
	}

	

	
	
	
}
