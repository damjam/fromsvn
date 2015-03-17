package com.ylink.cim.manage.dao.impl;

import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.hibernate.criterion.MatchMode;
import org.springframework.stereotype.Repository;

import com.ylink.cim.manage.dao.HouseInfoDao;
import com.ylink.cim.manage.domain.HouseInfo;

import flink.hibernate.BaseDaoHibernateImpl;
import flink.hibernate.QueryHelper;
import flink.util.Pager;
import flink.util.Paginater;
@Repository("houseInfoDao")
public class HouseInfoDaoImpl extends BaseDaoHibernateImpl implements HouseInfoDao{
	public Paginater findPager(Map<String, Object> params, Pager pager){
		QueryHelper helper = new QueryHelper();
		helper.append("from HouseInfo t where 1=1");
		helper.append("and t.houseSn like ?", MapUtils.getString(params, "houseSn"), MatchMode.START);
		helper.append("and t.buildingNo = ?", params.get("buildingNo"));
		helper.append("and t.unitNo = ?", params.get("unitNo"));
		helper.append("and t.floor = ?", params.get("floor"));
		helper.append("and t.branchNo = ?", MapUtils.getString(params, "branchNo"));
		helper.append("order by t.createDate desc");
		return super.getPageData(helper, pager);
	}

	@Override
	protected Class getModelClass() {
		return HouseInfo.class;
	}
	
}
