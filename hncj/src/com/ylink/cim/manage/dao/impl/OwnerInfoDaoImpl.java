package com.ylink.cim.manage.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.hibernate.criterion.MatchMode;
import org.springframework.stereotype.Repository;

import com.ylink.cim.common.state.OwnerState;
import com.ylink.cim.manage.dao.OwnerInfoDao;
import com.ylink.cim.manage.domain.OwnerInfo;

import flink.hibernate.BaseDaoHibernateImpl;
import flink.hibernate.QueryHelper;
import flink.util.Pager;
import flink.util.Paginater;
@Repository("ownerInfoDao")
public class OwnerInfoDaoImpl extends BaseDaoHibernateImpl implements OwnerInfoDao{
	public Paginater findPager(Map<String, Object> params, Pager pager){
		QueryHelper helper = new QueryHelper();
		helper.append("from OwnerInfo t where 1=1");
		helper.append("and t.ownerName like ?", MapUtils.getString(params, "ownerName"), MatchMode.START);
		helper.append("and t.houseSn like ?", MapUtils.getString(params, "houseSn"), MatchMode.START);
		helper.append("order by t.createDate desc");
		return super.getPageData(helper, pager);
	}

	@Override
	protected Class getModelClass() {
		return OwnerInfo.class;
	}

	public List<OwnerInfo> findByInfos(Map<String, Object> params) {
		QueryHelper helper = new QueryHelper();
		helper.append("from OwnerInfo t where 1=1");
		helper.append("and houseSn = ?", MapUtils.getString(params, "houseSn"));
		helper.append("and state = ?", OwnerState.NORMAL.getValue());
		return super.getList(helper);
	}

	public OwnerInfo getNormalOwner(String houseSn) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("houseSn", houseSn);
		params.put("state", OwnerState.NORMAL.getValue());
		return (OwnerInfo)getUniqueResult(OwnerInfo.class, params);
	}
	
}
