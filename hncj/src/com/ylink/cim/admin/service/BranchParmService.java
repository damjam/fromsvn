package com.ylink.cim.admin.service;

import com.ylink.cim.admin.domain.BranchParm;

import flink.etc.BizException;
import flink.util.Pager;
import flink.util.Paginater;

/**
 * ϵͳ��������
 * 
 * 
 */
public interface BranchParmService {

	public void delete(String id) throws BizException;

	public Paginater findAll(Pager pager, BranchParm sysParm) throws BizException;

	/**
	 * ����ָ����¼
	 * 
	 * @param code
	 * @return
	 * @throws BizException
	 */
	public BranchParm findByIdWithLock(String code) throws BizException;

	public void save(BranchParm sysParm) throws BizException;

	public void update(BranchParm sysParm) throws BizException;

}
