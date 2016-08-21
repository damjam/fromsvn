package com.ylink.cim.admin.dao;

import java.util.List;

import com.ylink.cim.admin.domain.LimitGroup;

import flink.hibernate.BaseDao;

public interface LimitGroupDao extends BaseDao {

	public void deleteByLimitGroupId(String limitGroupId);

	public List<LimitGroup> getByLimitGroupId(String limitGroupId);

	public List<LimitGroup> getLimitGroup(LimitGroup limitGroup);
}
