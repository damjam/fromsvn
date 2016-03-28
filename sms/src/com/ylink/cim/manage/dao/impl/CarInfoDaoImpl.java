package com.ylink.cim.manage.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Repository;

import com.ylink.cim.manage.dao.CarInfoDao;
import com.ylink.cim.manage.domain.CarInfo;

import flink.hibernate.BaseDaoImpl;
import flink.hibernate.QueryHelper;
import flink.util.Pager;
import flink.util.Paginater;

@Repository("carInfoDao")
public class CarInfoDaoImpl extends BaseDaoImpl implements CarInfoDao {

	@Override
	public List<CarInfo> findList(Map<String, Object> params) {
		QueryHelper helper = new QueryHelper();
		helper.append("from CarInfo t where 1=1");
		helper.append("and parkingSn = ?",
				MapUtils.getString(params, "parkingSn"));
		helper.append("and carSn = ?", MapUtils.getString(params, "carSn"));
		helper.append("and branchNo = ?",
				MapUtils.getString(params, "branchNo"));
		return super.getList(helper);
	}

	@Override
	public Paginater findPager(Map<String, Object> params, Pager pager) {
		QueryHelper helper = new QueryHelper();
		helper.append("from CarInfo t where 1=1");
		helper.append("and carSn like ?", MapUtils.getString(params, "carSn"));
		helper.append("and houseSn = ?", MapUtils.getString(params, "houseSn"));
		helper.append("and ownerName = ?",
				MapUtils.getString(params, "ownerName"));
		helper.append("and ownerCel = ?",
				MapUtils.getString(params, "ownerCel"));
		helper.append("and brand = ?", MapUtils.getString(params, "brand"));
		helper.append("and model = ?", MapUtils.getString(params, "model"));
		helper.append("and branchNo = ?", MapUtils.getString(params, "branchNo"));
		helper.append("order by createDate desc");
		return super.getPageData(helper, pager);
	}

	@Override
	protected Class getModelClass() {
		return CarInfo.class;
	}

}
