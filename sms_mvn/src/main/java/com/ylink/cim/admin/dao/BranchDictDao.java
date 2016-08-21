package com.ylink.cim.admin.dao;

import java.util.List;
import java.util.Map;

import com.ylink.cim.admin.domain.BranchDict;
import com.ylink.cim.admin.domain.BranchDictId;

import flink.hibernate.BaseDao;
import flink.util.Pager;
import flink.util.Paginater;

public interface BranchDictDao extends BaseDao {

	public List<BranchDict> findByParam(Map<String, Object> map);

	public List<BranchDict> getDictByDictType(String dictType);

	public Paginater getDictPageList(BranchDict branchDict, Pager pager);

}
