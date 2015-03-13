package com.ylink.cim.admin.service;

import com.ylink.cim.admin.domain.SysParm;

import flink.etc.BizException;
import flink.util.Pager;
import flink.util.Paginater;

/**
 * 系统参数管理
 * 
 * @author aps-lyg
 * 
 */
public interface SysParmService {

	public void delete(String id) throws BizException;

	public Paginater findAll(Pager pager, SysParm sysParm) throws BizException;

	public SysParm findById(String id) throws BizException;

	/**
	 * 锁定指定记录
	 * 
	 * @param code
	 * @return
	 * @throws BizException
	 */
	public SysParm findByIdWithLock(String code) throws BizException;

	public void save(SysParm sysParm) throws BizException;

	public void update(SysParm sysParm) throws BizException;

	
}
