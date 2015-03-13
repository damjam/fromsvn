package com.ylink.cim.admin.service;

import java.util.List;

import com.ylink.cim.admin.domain.LimitGroup;

import flink.etc.BizException;

public interface LimitGroupService {
	
	public List<LimitGroup> getByLimitGroupId(String limitGroupId) throws BizException;
	
	public List<LimitGroup> getLimitGroup(LimitGroup limitGroup) throws BizException;
}
