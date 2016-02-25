package com.ylink.cim.admin.service;

import com.ylink.cim.admin.domain.BranchDict;
import com.ylink.cim.admin.domain.BranchDictId;

import flink.etc.BizException;

public interface BranchDictService {

	public void deleteBranchDict(BranchDict branchDict) throws BizException;
	
	public void saveDict(BranchDict branchDict) throws BizException;
	
	public boolean isExist(BranchDictId id) throws BizException;

}
