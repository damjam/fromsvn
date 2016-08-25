package com.ylink.cim.admin.dao.impl;

import org.hibernate.criterion.MatchMode;
import org.springframework.stereotype.Component;

import com.ylink.cim.admin.dao.BranchParmDao;
import com.ylink.cim.admin.domain.BranchParam;

import flink.hibernate.BaseDaoImpl;
import flink.hibernate.QueryHelper;
import flink.util.Pager;
import flink.util.Paginater;

@Component("branchParmDao")
public class BranchParmDaoImpl extends BaseDaoImpl implements BranchParmDao {

	
	@Override
	protected Class<?> getModelClass() {
		return BranchParam.class;
	}

	@Override
	public Paginater getPageList(Pager pager, BranchParam branchParam) throws Exception {

		QueryHelper helper = new QueryHelper();
		helper.append("from BranchParam where 1=1");
		helper.append("and code like ?", branchParam.getCode(), MatchMode.ANYWHERE);
		helper.append("and parname like ?", branchParam.getParname(), MatchMode.ANYWHERE);
		helper.append("and branchNo = ?", branchParam.getBranchNo());
		return this.getPageData(helper, pager);
	}


}
