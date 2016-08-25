package com.ylink.cim.manage.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Repository;

import com.ylink.cim.manage.dao.ParkingInfoDao;
import com.ylink.cim.manage.domain.ParkingInfo;

import flink.hibernate.BaseDaoImpl;
import flink.hibernate.QueryHelper;
import flink.util.Pager;
import flink.util.Paginater;

@Repository("parkingInfoDao")
public class ParkingInfoDaoImpl extends BaseDaoImpl implements ParkingInfoDao {

	@Override
	protected Class getModelClass() {
		return ParkingInfo.class;
	}

	@Override
	public Paginater findPager(Map<String, Object> params, Pager pager) {
		QueryHelper helper = new QueryHelper();
		helper.append("from ParkingInfo t where 1=1");
		helper.append("and sn = ?", MapUtils.getString(params, "sn"));
		helper.append("and houseSn = ?", MapUtils.getString(params, "houseSn"));
		helper.append("and ownerName = ?",
				MapUtils.getString(params, "ownerName"));
		helper.append("and ownerCel = ?",
				MapUtils.getString(params, "ownerCel"));
		helper.append("and endUser = ?", MapUtils.getString(params, "endUser"));
		helper.append("and endUserCel = ?",
				MapUtils.getString(params, "endUserCel"));
		helper.append("and state = ?", MapUtils.getString(params, "state"));
		helper.append("and branchNo = ?", MapUtils.getString(params, "branchNo"));
		if (MapUtils.getBooleanValue(params, "avai")) {
			helper.append("and sn not in (select parkingSn from CarInfo)");
		}
		helper.append("order by createDate desc");
		return super.getPageData(helper, pager);
	}

	@Override
	public List<ParkingInfo> findBy(Map<String, Object> params) {
		QueryHelper helper = new QueryHelper();
		helper.append("from ParkingInfo t where 1=1");
		helper.append("and sn = ?", MapUtils.getString(params, "sn"));
		helper.append("and branchNo = ?",
				MapUtils.getString(params, "branchNo"));
		return super.getList(helper);
	}

}
