package com.ylink.cim.manage.dao.impl;

import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Component;

import com.ylink.cim.manage.dao.CarModelDao;
import com.ylink.cim.manage.domain.CarModel;

import flink.hibernate.BaseDaoImpl;
import flink.hibernate.QueryHelper;
import flink.util.Pager;
import flink.util.Paginater;
@Component("carModelDao")
public class CarModelDaoImpl extends BaseDaoImpl implements CarModelDao {

	@Override
	protected Class getModelClass() {
		return CarModel.class;
	}

	@Override
	public Paginater findPaginater(Map<String, Object> map, Pager pager) {
		QueryHelper helper = new QueryHelper();
		helper.append("from CarModel where 1=1");
		helper.append("and brandId = ?", MapUtils.getString(map, "brandId"));
		helper.append("and name like ?",MapUtils.getString(map, "name"));
		helper.append("and firstLetters like ?", MapUtils.getString(map, "firstLetters"));
		return super.getPageData(helper, pager);
	}

}
