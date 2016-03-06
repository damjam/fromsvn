package com.ylink.cim.manage.dao.impl;

import java.util.List;
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

	@Override
	public RepairTrack findLast(String id) {
		QueryHelper helper = new QueryHelper();
		helper.append("from RepairTrack where 1=1");
		helper.append("and repairId = ?", id);
		helper.append("order by id desc");
		List<RepairTrack> list = super.getList(helper);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<RepairTrack> findList(String repairId) {
		QueryHelper helper = new QueryHelper();
		helper.append("from RepairTrack where 1=1");
		helper.append("and repairId = ?", repairId);
		return super.getList(helper);
	}

}
