package com.ylink.cim.busioper.dao.impl;


import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Component;

import com.ylink.cim.busioper.dao.PushPlanDao;
import com.ylink.cim.busioper.domain.PushPlan;
import com.ylink.cim.common.type.BranchType;

import flink.hibernate.BaseDaoImpl;
import flink.hibernate.QueryHelper;
import flink.util.DateUtil;
import flink.util.Pager;
import flink.util.Paginater;
@Component("pushPlanDao")
public class PushPlanDaoImpl extends BaseDaoImpl implements PushPlanDao {

	public Paginater findPaginater(Map<String, Object> map, Pager pager) {
		QueryHelper helper = new QueryHelper();
		helper.append("from PushPlan where 1=1");
		if (map.get("startCreateDate") != null) {
			helper.append("and createTime >= ?", DateUtil.getDayEndByYYYMMDD(MapUtils.getString(map, "startCreateDate")));
		}
		if (map.get("endCreateDate") != null) {
			helper.append("and createTime <= ?", DateUtil.getDayEndByYYYMMDD(MapUtils.getString(map, "endCreateDate")));
		}
		if (!BranchType.HQ_0000.getValue().equals(map.get("branchNo"))) {
			helper.append("and branchNo = ?", map.get("branchNo"));
		}
		helper.append("order by createTime desc");
		return super.getPageData(helper, pager);
	}

	
	protected Class getModelClass() {
		return PushPlan.class;
	}


}
