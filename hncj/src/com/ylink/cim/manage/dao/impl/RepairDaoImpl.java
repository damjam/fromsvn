package com.ylink.cim.manage.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Component;

import com.ylink.cim.manage.dao.RepairDao;
import com.ylink.cim.manage.domain.Repair;
import com.ylink.cim.manage.domain.RepairTrack;

import flink.hibernate.BaseDaoImpl;
import flink.hibernate.QueryHelper;
import flink.util.Pager;
import flink.util.Paginater;
@Component("repairDao")
public class RepairDaoImpl extends BaseDaoImpl implements RepairDao {

	@Override
	protected Class getModelClass() {
		return Repair.class;
	}

	@Override
	public Paginater findPager(Map<String, Object> map, Pager pager) {
		QueryHelper helper = new QueryHelper();
		helper.append("from Repair where 1=1");
		helper.append("and state = ?",MapUtils.getString(map, "state"));
		helper.append("and branchNo = ?", MapUtils.getString(map, "branchNo"));
		return super.getPageData(helper, pager);
	}

}
