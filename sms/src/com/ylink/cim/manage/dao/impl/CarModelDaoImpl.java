package com.ylink.cim.manage.dao.impl;

import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.hibernate.criterion.MatchMode;
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
		String name = MapUtils.getString(map, "name");
		helper.append("from CarModel where 1=1");
		helper.append("and brand = ?", MapUtils.getString(map, "brand"));
		helper.append("and (name like ?", name, MatchMode.START);
		helper.append("or firstLetters like ?", name, MatchMode.START);
		helper.append("or pinyin like ?", name, MatchMode.START);
		helper.append("or modelEn like ?)", name, MatchMode.START);
		return super.getPageData(helper, pager);
	}
	public Paginater findByKeyword(String keyword, Pager pager) {
		QueryHelper helper = new QueryHelper();
		helper.append("from CarModel where 1=1");
		helper.append("and (name like ?", keyword.toLowerCase(), MatchMode.START);
		helper.append("or firstLetters like ?", keyword.toLowerCase(), MatchMode.START);
		helper.append("or pinyin like ?", keyword.toLowerCase(), MatchMode.START);
		helper.append("or modelEn like ?)", keyword.toLowerCase(), MatchMode.START);
		return super.getPageData(helper, pager);
	}

	@Override
	public boolean isExist(String name, String brand) {
		QueryHelper helper = new QueryHelper();
		helper.append("from CarModel where 1=1");
		helper.append("and name = ?", name);
		helper.append("and brand = ?", brand);
		//helper.append("or modelEn = ?)", modelEn);
		return super.getList(helper).size() > 0;
	}

}
