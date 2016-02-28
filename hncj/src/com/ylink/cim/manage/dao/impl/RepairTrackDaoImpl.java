package com.ylink.cim.manage.dao.impl;

import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Component;

import com.ylink.cim.manage.dao.RepairTrackDao;
import com.ylink.cim.manage.domain.RepairTrack;

import flink.hibernate.BaseDaoImpl;
import flink.hibernate.QueryHelper;
import flink.util.Pager;
import flink.util.Paginater;
@Component("repairTrackDao")
public class RepairTrackDaoImpl extends BaseDaoImpl implements RepairTrackDao {

	@Override
	protected Class getModelClass() {
		return RepairTrack.class;
	}

	@Override
	public Paginater findPager(Map<String, Object> map, Pager pager) {
		QueryHelper helper = new QueryHelper();
		helper.append("from RepairTrack where 1=1");
		helper.append("and repairId = ?", MapUtils.getString(map, "repairId"));
		return super.getPageData(helper, pager);
	}

	@Override
	public void deleteByPepairId(String id) {
		QueryHelper helper = new QueryHelper();
		helper.append("delete RepairTrack where repairId = ?", id);
		super.execute(helper);
	}

}
