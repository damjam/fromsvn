package com.ylink.cim.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ylink.cim.admin.dao.LimitGroupDao;
import com.ylink.cim.admin.domain.LimitGroup;
import com.ylink.cim.admin.service.LimitGroupService;

import flink.etc.BizException;

@Component("limitGroupService")
public class LimitGroupServiceImpl implements LimitGroupService {

	@Autowired
	private LimitGroupDao limitGroupDao;

	public void setLimitGroupDao(LimitGroupDao limitGroupDao) {
		this.limitGroupDao = limitGroupDao;
	}

	public List<LimitGroup> getByLimitGroupId(String limitGroupId) throws BizException {

		return this.limitGroupDao.getByLimitGroupId(limitGroupId);
	}

	public List<LimitGroup> getLimitGroup(LimitGroup limitGroup) throws BizException {
		return this.limitGroupDao.getLimitGroup(limitGroup);
	}

}
