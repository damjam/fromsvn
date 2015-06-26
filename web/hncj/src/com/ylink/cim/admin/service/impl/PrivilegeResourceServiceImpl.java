package com.ylink.cim.admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ylink.cim.admin.dao.PrivilegeResourceDao;
import com.ylink.cim.admin.domain.PrivilegeResource;
import com.ylink.cim.admin.service.PrivilegeResourceService;

import flink.etc.BizException;
import flink.util.Pager;
import flink.util.Paginater;

@Component("privilegeResourceService")
public class PrivilegeResourceServiceImpl implements PrivilegeResourceService {

	@Autowired
	private PrivilegeResourceDao privilegeResourceDao;

	public void setPrivilegeResourceDao(PrivilegeResourceDao privilegeResourceDao) {
		this.privilegeResourceDao = privilegeResourceDao;
	}

	public void deletePrivilegeResource(PrivilegeResource privilegeResource) throws BizException {
		this.privilegeResourceDao.deleteById(privilegeResource.getId());

	}

	public Paginater getPrivilegeResourcePageList(PrivilegeResource privilegeResource, Pager pager) {

		return this.privilegeResourceDao.getPrivilegeResourceList(privilegeResource, pager);
	}

	public void savePrivilegeResource(PrivilegeResource privilegeResource) throws BizException {

		this.privilegeResourceDao.save(privilegeResource);

	}

	public PrivilegeResource getPrivilegeResource(Long id) throws BizException {

		return this.privilegeResourceDao.findById(id);
	}

}
