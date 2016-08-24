package com.ylink.cim.manage.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Component;

import com.ylink.cim.manage.dao.RenterInfoDao;
import com.ylink.cim.manage.domain.RenterInfo;

import flink.hibernate.BaseDaoImpl;
import flink.hibernate.QueryHelper;
import flink.util.Pager;
import flink.util.Paginater;
@Component("rentInfoDao")
public class RenterInfoDaoImpl extends BaseDaoImpl implements RenterInfoDao {

	@Override
	protected Class getModelClass() {
		return RenterInfo.class;
	}

	@Override
	public Paginater findPaginater(Map<String, Object> params, Pager pager) {
		QueryHelper helper = new QueryHelper();
		helper.append("from RenterInfo where 1=1");
		
		return super.getPageData(helper, pager);
	}

	@Override
	public List<RenterInfo> findList(Map<String, Object> map) {
		QueryHelper helper = new QueryHelper();
		helper.append("from RenterInfo where 1=1");
		helper.append("and houseSn = ?", MapUtils.getString(map, "houseSn"));
		helper.append("and state = ?", MapUtils.getString(map, "state"));
		return super.getList(helper);
	}

}
