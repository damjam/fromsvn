package com.ylink.cim.admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ylink.cim.admin.dao.BranchDictDao;
import com.ylink.cim.admin.domain.BranchDict;
import com.ylink.cim.admin.domain.BranchDictId;
import com.ylink.cim.admin.service.BranchDictService;

import flink.etc.BizException;

@Component("branchDictService")
public class BranchDictServiceImpl implements BranchDictService {
	@Autowired
	private BranchDictDao branchDictDao;
	@Override
	public void deleteBranchDict(BranchDict branchDict) throws BizException {
		this.branchDictDao.deleteById(branchDict.getId());
	}
	@Override
	public void saveDict(BranchDict branchDict) throws BizException {
		branchDictDao.save(branchDict);
	}
	@Override
	public boolean isExist(BranchDictId id) throws BizException {
		return branchDictDao.findById(id) != null;
	}
	
}
