package com.ylink.cim.admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ylink.cim.admin.dao.PrivilegeDao;
import com.ylink.cim.admin.domain.Privilege;
import com.ylink.cim.admin.service.PrivilegeService;

import flink.etc.BizException;

@Component("privilegeService")
public class PrivilegeServiceImpl implements PrivilegeService {

	@Autowired
	private PrivilegeDao privilegeDao;

	@Override
	public void savePrivilege(Privilege privilege) throws BizException {

		if (null != this.privilegeDao.findById(privilege.getLimitId())) {
			throw new BizException("权限点  " + privilege.getLimitId() + ",已经存在");
		}

		this.privilegeDao.save(privilege);

	}

	@Override
	public void delete(String id) throws BizException {
		this.privilegeDao.deleteById(id);
	}

}
