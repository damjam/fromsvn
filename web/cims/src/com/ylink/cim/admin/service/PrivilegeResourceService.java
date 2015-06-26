package com.ylink.cim.admin.service;

import com.ylink.cim.admin.domain.PrivilegeResource;

import flink.etc.BizException;
import flink.util.Pager;
import flink.util.Paginater;

public interface PrivilegeResourceService {

	public Paginater getPrivilegeResourcePageList(PrivilegeResource privilegeResource,Pager pager);
	
	public void savePrivilegeResource(PrivilegeResource privilegeResource) throws BizException;
	
	public void deletePrivilegeResource(PrivilegeResource privilegeResource) throws BizException;
	
	public PrivilegeResource getPrivilegeResource(Long id) throws BizException;
}
