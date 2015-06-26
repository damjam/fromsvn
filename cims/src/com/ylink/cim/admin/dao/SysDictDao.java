package com.ylink.cim.admin.dao;

import java.util.List;
import java.util.Map;

import com.ylink.cim.admin.domain.SysDict;

import flink.hibernate.BaseDao;
import flink.util.Pager;
import flink.util.Paginater;

public interface SysDictDao extends BaseDao {

	public Paginater getSysDictPageList(SysDict sysDict, Pager pager);

	public List<SysDict> getSysDictByDictType(String dictType);

	public List<SysDict> findByParam(Map<String, Object> map);
}
