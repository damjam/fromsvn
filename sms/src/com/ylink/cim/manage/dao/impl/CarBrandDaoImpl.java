package com.ylink.cim.manage.dao.impl;

import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Component;

import com.ylink.cim.manage.dao.CarBrandDao;
import com.ylink.cim.manage.domain.CarBrand;

import flink.hibernate.BaseDaoImpl;
import flink.hibernate.QueryHelper;
import flink.util.Pager;
import flink.util.Paginater;
@Component("carBrandDao")
public class CarBrandDaoImpl extends BaseDaoImpl implements CarBrandDao {

	@Override
	protected Class getModelClass() {
		return CarBrand.class;
	}

	@Override
	public Paginater findPaginater(Map<String, Object> map, Pager pager) {
		QueryHelper helper = new QueryHelper();
		helper.append("from CarBrand where 1=1");
		helper.append("and brand like ?", MapUtils.getString(map, "brand"));
		helper.append("and country = ?",MapUtils.getString(map, "country"));
		helper.append("and firstLetters like ?", MapUtils.getString(map, "firstLetters"));
		return super.getPageData(helper, pager);
	}

}
