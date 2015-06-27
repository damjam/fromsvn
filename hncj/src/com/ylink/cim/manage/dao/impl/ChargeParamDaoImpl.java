package com.ylink.cim.manage.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.MatchMode;
import org.springframework.stereotype.Component;

import com.ylink.cim.common.type.BranchType;
import com.ylink.cim.manage.dao.ChargeParamDao;
import com.ylink.cim.manage.domain.ChargeParam;

import flink.hibernate.BaseDaoImpl;
import flink.hibernate.QueryHelper;
import flink.util.Pager;
import flink.util.Paginater;

@Component("chargeParamDao")
public class ChargeParamDaoImpl extends BaseDaoImpl implements ChargeParamDao {

	@Override
	public List<ChargeParam> findBy(Map<String, Object> params) {
		QueryHelper helper = new QueryHelper();
		helper.append("from ChargeParam where 1=1");
		helper.append("and rangeCode = ?",
				MapUtils.getString(params, "rangeCode"));
		helper.append("and chargeObj = ?",
				MapUtils.getString(params, "chargeObj"));
		helper.append("and paramName like ?",
				MapUtils.getString(params, "paramName"), MatchMode.START);
		if (!StringUtils.equals(BranchType.HQ_0000.getValue(),
				MapUtils.getString(params, "branchNo"))) {
			helper.append("and branchNo = ?",
					MapUtils.getString(params, "branchNo"));
		}
		helper.append("and id <> ?", MapUtils.getString(params, "exceptId"));
		return super.getList(helper);
	}

	@Override
	public Paginater findPager(Map<String, Object> params, Pager pager) {
		QueryHelper helper = new QueryHelper();
		helper.append("from ChargeParam where 1=1");
		helper.append("and rangeCode = ?",
				MapUtils.getString(params, "rangeCode"));
		helper.append("and paramName like ?",
				MapUtils.getString(params, "paramName"), MatchMode.START);
		if (!StringUtils.equals(BranchType.HQ_0000.getValue(),
				MapUtils.getString(params, "branchNo"))) {
			helper.append("and branchNo = ?",
					MapUtils.getString(params, "branchNo"));
		}
		return super.getPageData(helper, pager);
	}

	@Override
	protected Class getModelClass() {
		// TODO Auto-generated method stub
		return ChargeParam.class;
	}

}
