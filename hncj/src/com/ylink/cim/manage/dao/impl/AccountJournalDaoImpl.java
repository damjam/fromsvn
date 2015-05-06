package com.ylink.cim.manage.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.ylink.cim.common.type.BranchType;
import com.ylink.cim.common.type.InoutType;
import com.ylink.cim.common.type.TradeType;
import com.ylink.cim.manage.dao.AccountJournalDao;
import com.ylink.cim.manage.domain.AccountJournal;

import flink.hibernate.BaseDaoHibernateImpl;
import flink.hibernate.QueryHelper;
import flink.util.AmountUtils;
import flink.util.DateUtil;
import flink.util.Pager;
import flink.util.Paginater;

@Repository("accountJournalDao")
public class AccountJournalDaoImpl extends BaseDaoHibernateImpl implements AccountJournalDao {

	public List<Map<String, Object>> findGatherInfo(Map<String, Object> params) {
		QueryHelper helper = new QueryHelper();
		helper.append("select new map(count(t.id) as cnt, sum(t.amount) as sumAmt, t.tradeType as tradeType, t.inoutType as inoutType) from AccountJournal t where 1=1");
		helper.append("and t.tradeType = ?", MapUtils.getString(params, "tradeType"));
		helper.append("and t.inoutType = ?", MapUtils.getString(params, "inoutType"));
		helper.append("and t.tradeType <> ?", TradeType.INNER_WITHDRAW.getValue());
		if (!StringUtils.equals(BranchType.HQ_0000.getValue(), MapUtils.getString(params, "branchNo"))) {
			helper.append("and branchNo = ?", MapUtils.getString(params, "branchNo"));
		}
		String gatherWay = MapUtils.getString(params, "gatherWay");
		String gatherPeriod = MapUtils.getString(params, "gatherPeriod");
		if (!StringUtils.isBlank(gatherPeriod)) {
			String pattern = "";
			Date endDate = null;
			if ("D".equals(gatherWay)) {
				pattern = "yyyyMMdd";
			} else if ("M".equals(gatherWay)) {
				pattern = "yyyyMM";

			} else if ("Y".equals(gatherWay)) {
				pattern = "yyyy";
			}
			Date beginDate = DateUtil.string2Date(gatherPeriod, pattern);
			endDate = DateUtil.getNextDate(gatherPeriod, pattern);
			helper.append("and t.createDate >= ?", beginDate);
			helper.append("and t.createDate <= ?", endDate);
		}
		helper.append("group by t.tradeType");
		List<Map<String, Object>> sumList = super.getList(helper);
		return sumList;
		/*
		 * Map<String, Object> sumInfo = new HashMap<String, Object>(); Long
		 * totalCnt = 0L; Double totalAmt = 0d; Long inCnt = 0L; Double inAmt =
		 * 0d; Long outCnt = 0L; Double outAmt = 0d; for (int i = 0; i <
		 * sumList.size(); i++) { Map<String, Object> map = sumList.get(i);
		 * String inoutType = (String)map.get("inoutType"); Long cnt =
		 * (Long)map.get("cnt"); Double sumAmt = (Double)map.get("sumAmt"); if
		 * (cnt == null) { cnt = 0L; } if (sumAmt == null) { sumAmt = 0d; } if
		 * (InoutType.TYPE_IN.getValue().equals(inoutType)) { inCnt = cnt; inAmt
		 * = sumAmt; }else { outCnt = cnt; outAmt = sumAmt; } totalCnt += cnt;
		 * totalAmt += sumAmt; } sumInfo.put("inCnt", inCnt);
		 * sumInfo.put("inAmt", inAmt); sumInfo.put("outCnt", outCnt);
		 * sumInfo.put("outAmt", outAmt); sumInfo.put("totalCnt", totalCnt);
		 * sumInfo.put("netAmt", AmountUtils.add(inAmt, -outAmt)); return
		 * sumInfo;
		 */
	}

	public Paginater findPager(Map<String, Object> params, Pager pager) {
		QueryHelper helper = new QueryHelper();
		helper.append("from AccountJournal where 1=1");
		helper.append("and tradeType = ?", MapUtils.getString(params, "tradeType"));
		helper.append("and inoutType = ?", MapUtils.getString(params, "inoutType"));
		if (StringUtils.isNotEmpty(MapUtils.getString(params, "startCreateDate"))) {
			helper.append("and createDate >= ?", DateUtil.formatDate(MapUtils.getString(params, "startCreateDate")));
		}
		if (StringUtils.isNotEmpty(MapUtils.getString(params, "endCreateDate"))) {
			helper.append("and createDate <= ?",
					DateUtil.getDayEndByYYYMMDD(MapUtils.getString(params, "endCreateDate")));
		}
		String year = MapUtils.getString(params, "year");
		if (!StringUtils.isBlank(year)) {
			helper.append("and createDate >= ?", year);
			Integer yearInt = Integer.parseInt(year);
			helper.append("and createDate <= ?", String.valueOf(yearInt + 1));
		}
		if (!StringUtils.equals(BranchType.HQ_0000.getValue(), MapUtils.getString(params, "branchNo"))) {
			helper.append("and branchNo = ?", MapUtils.getString(params, "branchNo"));
		}
		helper.append("order by id desc");
		return super.getPageData(helper, pager);
	}

	public Map<String, Object> findSumInfo(Map<String, Object> params) {
		QueryHelper helper = new QueryHelper();
		helper.append("select new map(count(t.id) as cnt, sum(t.amount) as sumAmt, t.inoutType as inoutType) from AccountJournal t where 1=1");
		helper.append("and t.tradeType = ?", MapUtils.getString(params, "tradeType"));
		helper.append("and t.inoutType = ?", MapUtils.getString(params, "inoutType"));
		helper.append("and t.tradeType <> ?", TradeType.INNER_WITHDRAW.getValue());
		if (StringUtils.isNotEmpty(MapUtils.getString(params, "startCreateDate"))) {
			helper.append("and t.createDate >= ?", DateUtil.formatDate(MapUtils.getString(params, "startCreateDate")));
		}
		if (StringUtils.isNotEmpty(MapUtils.getString(params, "endCreateDate"))) {
			helper.append("and t.createDate <= ?",
					DateUtil.getDayEndByYYYMMDD(MapUtils.getString(params, "endCreateDate")));
		}
		if (!StringUtils.equals(BranchType.HQ_0000.getValue(), MapUtils.getString(params, "branchNo"))) {
			helper.append("and branchNo = ?", MapUtils.getString(params, "branchNo"));
		}
		String year = MapUtils.getString(params, "year");
		if (!StringUtils.isBlank(year)) {
			helper.append("and t.createDate >= ?", year);
			Integer yearInt = Integer.parseInt(year);
			helper.append("and t.createDate <= ?", String.valueOf(yearInt + 1));
		}
		helper.append("group by t.inoutType");
		List<Map<String, Object>> sumList = super.getList(helper);
		Map<String, Object> sumInfo = new HashMap<String, Object>();
		Long totalCnt = 0L;
		Double totalAmt = 0d;
		Long inCnt = 0L;
		Double inAmt = 0d;
		Long outCnt = 0L;
		Double outAmt = 0d;
		for (int i = 0; i < sumList.size(); i++) {
			Map<String, Object> map = sumList.get(i);
			String inoutType = (String) map.get("inoutType");
			Long cnt = (Long) map.get("cnt");
			Double sumAmt = (Double) map.get("sumAmt");
			if (cnt == null) {
				cnt = 0L;
			}
			if (sumAmt == null) {
				sumAmt = 0d;
			}
			if (InoutType.TYPE_IN.getValue().equals(inoutType)) {
				inCnt = cnt;
				inAmt = sumAmt;
			} else {
				outCnt = cnt;
				outAmt = sumAmt;
			}
			totalCnt += cnt;
			totalAmt += sumAmt;
		}
		sumInfo.put("inCnt", inCnt);
		sumInfo.put("inAmt", inAmt);
		sumInfo.put("outCnt", outCnt);
		sumInfo.put("outAmt", outAmt);
		sumInfo.put("totalCnt", totalCnt);
		sumInfo.put("netAmt", AmountUtils.add(inAmt, -outAmt));
		return sumInfo;
	}

	@Override
	protected Class getModelClass() {
		return AccountJournal.class;
	}

}
