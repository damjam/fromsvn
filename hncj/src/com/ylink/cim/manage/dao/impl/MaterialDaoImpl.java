package com.ylink.cim.manage.dao.impl;

import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.hibernate.criterion.MatchMode;
import org.springframework.stereotype.Component;

import com.ylink.cim.admin.domain.UserInfo;
import com.ylink.cim.manage.dao.MaterialDao;
import com.ylink.cim.manage.domain.Material;

import flink.hibernate.BaseDaoImpl;
import flink.hibernate.QueryHelper;
import flink.util.Pager;
import flink.util.Paginater;
@Component("materialDao")
public class MaterialDaoImpl extends BaseDaoImpl implements MaterialDao {

	@Override
	protected Class getModelClass() {
		return Material.class;
	}

	@Override
	public Paginater findPaginater(Map<String, Object> map, UserInfo userInfo, Pager pager) {
		QueryHelper helper = new QueryHelper();
		helper.append("from Material where 1=1");
		helper.append("and item like ?", MapUtils.getString(map, "item"), MatchMode.ANYWHERE);
		helper.append("and state = ?", MapUtils.getString(map, "state"));
		helper.append("and branchNo = ?", MapUtils.getString(map, "branchNo"));
		if (userInfo != null) {
			helper.append("and branchNo = ?", MapUtils.getString(map, "branchNo"));
		}
		helper.append("and manager like ?", MapUtils.getString(map, "manager"), MatchMode.ANYWHERE);
		return super.getPageData(helper, pager);
	}
	
}
