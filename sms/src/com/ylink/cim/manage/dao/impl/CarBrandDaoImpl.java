package com.ylink.cim.manage.dao.impl;

import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.hibernate.criterion.MatchMode;
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
	public Paginater findByKeyword(String keyword, Pager pager) {
		QueryHelper helper = new QueryHelper();
		helper.append("from CarBrand where 1=1");
		helper.append("and (brand like ?", keyword.toLowerCase(), MatchMode.START);
		helper.append("or firstLetters like ?", keyword.toLowerCase(), MatchMode.START);
		helper.append("or pinyin like ?", keyword.toLowerCase(), MatchMode.START);
		helper.append("or id like ?)", keyword.toLowerCase(), MatchMode.START);
		return super.getPageData(helper, pager);
	}

	@Override
	public boolean isExist(String id, String brand) {
		QueryHelper helper = new QueryHelper();
		helper.append("from CarBrand where 1=1");
		helper.append("and (id = ?", id);
		helper.append("or brand = ?)", brand);
		return super.getList(helper).size() > 0;
	}

}
