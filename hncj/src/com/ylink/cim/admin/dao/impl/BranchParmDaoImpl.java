package com.ylink.cim.admin.dao.impl;

import org.hibernate.criterion.MatchMode;
import org.springframework.stereotype.Component;

import com.ylink.cim.admin.dao.BranchParmDao;
import com.ylink.cim.admin.domain.BranchParm;

import flink.hibernate.BaseDaoImpl;
import flink.hibernate.QueryHelper;
import flink.util.Pager;
import flink.util.Paginater;

@Component("branchParmDao")
public class BranchParmDaoImpl extends BaseDaoImpl implements BranchParmDao {

	
	@Override
	protected Class getModelClass() {
		return BranchParm.class;
	}

	@Override
	public Paginater getPageList(Pager pager, BranchParm sysParm) throws Exception {

		QueryHelper helper = new QueryHelper();
		helper.append("from BranchParm where 1=1");
		helper.append("and code like ?", sysParm.getCode(), MatchMode.ANYWHERE);
		helper.append("and parname like ?", sysParm.getParname(), MatchMode.ANYWHERE);
		return this.getPageData(helper, pager);
	}


}
