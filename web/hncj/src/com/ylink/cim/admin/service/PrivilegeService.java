package com.ylink.cim.admin.service;

import com.ylink.cim.admin.domain.Privilege;

import flink.etc.BizException;

/**
 * х╗оч
 * 
 *
 */
public interface PrivilegeService {
	
	public void savePrivilege(Privilege privilege) throws BizException;
	
	public void delete(String id) throws BizException;
	
}
