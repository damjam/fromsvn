package com.ylink.cim.admin.service;

import com.ylink.cim.admin.domain.BranchParam;

import flink.etc.BizException;
import flink.util.Pager;
import flink.util.Paginater;

/**
 * 系统参数管理
 * 
 * 
 */
public interface BranchParmService {

	public void delete(String id) throws BizException;

	public Paginater findAll(Pager pager, BranchParam sysParm) throws BizException;

	/**
	 * 锁定指定记录
	 * 
	 * @param code
	 * @return
	 * @throws BizException
	 */
	public BranchParam findByIdWithLock(String code) throws BizException;

	public void save(BranchParam sysParm) throws BizException;

	public void update(BranchParam sysParm) throws BizException;

}
