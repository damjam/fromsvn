package com.ylink.cim.admin.dao;

import com.ylink.cim.admin.domain.BranchParm;

import flink.hibernate.BaseDao;
import flink.util.Pager;
import flink.util.Paginater;


public interface BranchParmDao extends BaseDao {
	

	public Paginater getPageList(Pager pager, BranchParm sysParm)
			throws Exception;


	
}
