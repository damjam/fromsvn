package com.ylink.cim.manage.dao.impl;

import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.hibernate.criterion.MatchMode;
import org.springframework.stereotype.Component;

import com.ylink.cim.manage.dao.RentalHouseDao;
import com.ylink.cim.manage.domain.RentalHouse;

import flink.hibernate.BaseDaoImpl;
import flink.hibernate.QueryHelper;
import flink.util.Pager;
import flink.util.Paginater;
@Component("rentalHouseDao")
public class RentalHouseDaoImpl extends BaseDaoImpl implements RentalHouseDao {

	@Override
	protected Class getModelClass() {
		return RentalHouse.class;
	}

	@Override
	public Paginater findPaginater(Map<String, Object> params, Pager pager) {
		QueryHelper helper = new QueryHelper();
		helper.append("from RentalHouse where 1=1");
		helper.append("and state = ?", MapUtils.getString(params, "state"));
		helper.append("and houseSn like ?", MapUtils.getString(params, "houseSn"), MatchMode.START);
		helper.append("and branchNo = ?", MapUtils.getString(params, "branchNo"));
		helper.append("and buildingNo = ?", MapUtils.getString(params, "buildingNo"));
		helper.append("and roomConfig like ?",MapUtils.getString(params, "roomConfig"),MatchMode.ANYWHERE);
		return super.getPageData(helper, pager);
	}
}
