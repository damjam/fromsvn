package com.ylink.cim.manage.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.hibernate.criterion.MatchMode;
import org.springframework.stereotype.Component;

import com.ylink.cim.manage.dao.OrderRecordDao;
import com.ylink.cim.manage.domain.OrderRecord;

import flink.hibernate.BaseDaoImpl;
import flink.hibernate.QueryHelper;
import flink.util.Pager;
import flink.util.Paginater;
@Component("orderRecordDao")
public class OrderRecordDaoImpl extends BaseDaoImpl implements OrderRecordDao {

	@Override
	protected Class getModelClass() {
		return OrderRecord.class;
	}

	@Override
	public Paginater findPaginater(Map<String, Object> map, Pager pager) {
		QueryHelper helper = new QueryHelper();
		helper.append("from OrderRecord where 1=1");
		helper.append("and clientName like ?", MapUtils.getString(map, "clientName"), MatchMode.ANYWHERE);
		helper.append("and clientTel like ?",MapUtils.getString(map, "clientTel"), MatchMode.START);
		helper.append("and orderDate >= ?", MapUtils.getString(map, "beginOrderDate"));
		helper.append("and orderDate <= ?", MapUtils.getString(map, "endOrderDate"));
		helper.append("and payState = ?", MapUtils.getString(map, "payState"));
		helper.append("and branchNo = ?", MapUtils.getString(map, "branchNo"));
		helper.append("order by id desc");
		return super.getPageData(helper, pager);
	}

	@Override
	public Map<String, Object> findSumInfo(Map<String, Object> params) {
		QueryHelper helper = new QueryHelper();
		helper.append("select new map(count(t.id) as cnt, sum(t.amount) as sumAmt, t.state as state) from OrderRecord t where 1=1");
		helper.append("and orderDate >= ?",
				MapUtils.getString(params, "beginOrderDate"));
		helper.append("and orderDate <= ?",
				MapUtils.getString(params, "endOrderDate"));
		helper.append("and id = ?", MapUtils.getString(params, "id"));
		helper.append("and branchNo = ?",
				MapUtils.getString(params, "branchNo"));
		//helper.append("group by t.state");
		Map<String, Object> sumInfo = (Map<String, Object>)super.getUniqueResult(helper);
		sumInfo.put("totalCnt", (Long)sumInfo.get("cnt"));
		Double sumAmt = 0d;
		if(sumInfo.get("sumAmt") != null){
			sumAmt = (Double)sumInfo.get("sumAmt");
		}
		sumInfo.put("totalAmt", sumAmt);
		return sumInfo;
	}

	@Override
	public List<Map<String, Object>> findDailySum(String beginDate, String endDate) {
		QueryHelper helper = new QueryHelper();
		helper.append("select new map(orderDate as orderDate, payState as payState, count(id) as cnt, sum(amount) as amt) from OrderRecord where 1=1");
		helper.append("and orderDate >= ?", beginDate);
		helper.append("and orderDate <= ?", endDate);
		helper.append("group by orderDate, payState");
		//helper.append("order by id desc");
		return super.getList(helper);
	}

}
