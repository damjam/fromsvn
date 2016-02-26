package com.ylink.cim.admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ylink.cim.admin.dao.PrivilegeResourceDao;
import com.ylink.cim.admin.domain.PrivilegeResource;
import com.ylink.cim.admin.service.PrivilegeResourceService;

import flink.IdFactoryHelper;
import flink.consant.Constants;
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

	@Override
	public void deletePrivilegeResource(PrivilegeResource privilegeResource) throws BizException {
		this.privilegeResourceDao.deleteById(privilegeResource.getId());

	}

	@Override
	public Paginater getPrivilegeResourcePageList(PrivilegeResource privilegeResource, Pager pager) {

		return this.privilegeResourceDao.getPrivilegeResourceList(privilegeResource, pager);
	}

	@Override
	public void savePrivilegeResource(PrivilegeResource privilegeResource) throws BizException {
		if (privilegeResource.getId() == null || privilegeResource.getId() == 0) {
			String id = IdFactoryHelper.getId(Constants.PRIVILEGE_RESOURCE_ID);
			privilegeResource.setId(Long.parseLong(id));
			this.privilegeResourceDao.save(privilegeResource);
		}else {
			privilegeResourceDao.update(privilegeResource);
		}
	}

	@Override
	public PrivilegeResource getPrivilegeResource(Long id) throws BizException {

		return this.privilegeResourceDao.findById(id);
	}

}
