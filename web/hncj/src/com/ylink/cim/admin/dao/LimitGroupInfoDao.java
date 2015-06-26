package com.ylink.cim.admin.dao;

import java.util.List;

import com.ylink.cim.admin.domain.LimitGroupInfo;
import com.ylink.cim.admin.domain.SysDict;

import flink.hibernate.BaseDao;
import flink.util.Pager;
import flink.util.Paginater;

public interface LimitGroupInfoDao extends BaseDao {

	public Paginater getLimitGroupInfoPageList(LimitGroupInfo limitGroupInfo, Pager pager);

	public List<SysDict> getSysDictNoLimitGroup();

	public List<LimitGroupInfo> getAll();
}
