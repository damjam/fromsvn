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

	public void savePrivilege(Privilege privilege) throws BizException {
		
		if(null!=this.privilegeDao.findById(privilege.getLimitId())){
			throw new BizException("Ȩ�޵�  "+privilege.getLimitId()+",�Ѿ�����");
		}
		
		this.privilegeDao.save(privilege);
		
	}

	public void delete(String id) throws BizException {
		this.privilegeDao.deleteById(id);
	}

	
}
