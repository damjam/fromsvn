package com.ylink.cim.manage.dao.impl;

import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.ylink.cim.common.type.BranchType;
import com.ylink.cim.manage.dao.CarInfoDao;
import com.ylink.cim.manage.domain.CarInfo;

import flink.hibernate.BaseDaoHibernateImpl;
import flink.hibernate.QueryHelper;
import flink.util.Pager;
import flink.util.Paginater;
@Repository("carInfoDao")
public class CarInfoDaoImpl extends BaseDaoHibernateImpl implements CarInfoDao {

	@Override
	protected Class getModelClass() {
		return CarInfo.class;
	}

	public Paginater findPager(Map<String, Object> params, Pager pager) {
		QueryHelper helper = new QueryHelper();
		helper.append("from CarInfo t where 1=1");
		helper.append("and carSn like ?", MapUtils.getString(params, "carSn"));
		helper.append("and houseSn = ?", MapUtils.getString(params, "houseSn"));
		helper.append("and ownerName = ?", MapUtils.getString(params, "ownerName"));
		helper.append("and ownerCel = ?", MapUtils.getString(params, "ownerCel"));
		helper.append("and brand = ?", MapUtils.getString(params, "brand"));
		helper.append("and model = ?", MapUtils.getString(params, "model"));
		if (!StringUtils.equals(BranchType.HQ_0000.getValue(), MapUtils.getString(params, "branchNo"))) {
			helper.append("and branchNo = ?", MapUtils.getString(params, "branchNo"));
		}
		helper.append("order by createDate desc");
		return super.getPageData(helper, pager);
	}


	
}
