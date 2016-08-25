package com.ylink.cim.admin.service;

import com.ylink.cim.admin.domain.BranchParam;

import flink.etc.BizException;
import flink.util.Pager;
import flink.util.Paginater;

/**
 * 
 * 
 */
public interface BranchParmService {

	public void delete(String id) throws BizException;

	public Paginater findAll(Pager pager, BranchParam sysParm) throws BizException;

	/**
	 * 
	 * @param code
	 * @return
	 * @throws BizException
	 */
	public BranchParam findByIdWithLock(String code) throws BizException;

	public void save(BranchParam sysParm) throws BizException;

	public void update(BranchParam sysParm) throws BizException;

}
