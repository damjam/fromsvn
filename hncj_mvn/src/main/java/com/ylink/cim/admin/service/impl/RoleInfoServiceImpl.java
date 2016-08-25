package com.ylink.cim.admin.service.impl;

import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ylink.cim.admin.dao.RoleInfoDao;
import com.ylink.cim.admin.dao.RolePrivilegeDao;
import com.ylink.cim.admin.domain.RoleInfo;
import com.ylink.cim.admin.domain.RolePrivilege;
import com.ylink.cim.admin.domain.RolePrivilegeId;
import com.ylink.cim.admin.service.RoleInfoService;

import flink.consant.ActionMessageConstant;
import flink.etc.BizException;
import flink.util.ExceptionUtils;
import flink.util.Pager;
import flink.util.Paginater;

@Component("roleInfoService")
public class RoleInfoServiceImpl implements RoleInfoService {

	@Autowired
	private RoleInfoDao roleInfoDao;
	@Autowired
	private RolePrivilegeDao rolePrivilegeDao;
	
	public void setRoleInfoDao(RoleInfoDao roleInfoDao) {
		this.roleInfoDao = roleInfoDao;
	}
	
	
	public void setRolePrivilegeDao(RolePrivilegeDao rolePrivilegeDao) {
		this.rolePrivilegeDao = rolePrivilegeDao;
	}
	
	@Override
	public Paginater getRoleInfoPageList(RoleInfo roleInfo, Pager pager)
			throws BizException {
		
		return this.roleInfoDao.getRoleInfoPageList(roleInfo, pager);
	}

	@Override
	public void deleteRoleInfo(RoleInfo roleInfo) throws BizException {
		try{
			this.roleInfoDao.deleteRoleLimits(roleInfo);
			roleInfoDao.delete(roleInfo);
		}catch (Exception e) {
			ExceptionUtils.logBizException(RoleInfoServiceImpl.class, e.getMessage());
		}
	}

	@Override
	public void saveRoleAndLimits(RoleInfo roleInfo) throws BizException {
		roleInfoDao.save(roleInfo);
		this.saveRoleLimit(roleInfo);
	}
	public void saveRoleLimit(RoleInfo roleInfo) throws BizException {
		 
		try{
			if(ArrayUtils.isEmpty(roleInfo.getLimitIds())){
				throw new BizException(ActionMessageConstant.OPER_FAIL_NO_LIMIT);
			}
			for(String limitId:roleInfo.getLimitIds()){
				RolePrivilegeId id=new RolePrivilegeId();
				id.setLimitId(limitId);
				id.setRoleId(roleInfo.getRoleId());
				
				RolePrivilege rolePrivilege=new RolePrivilege();
				rolePrivilege.setId(id);
				this.rolePrivilegeDao.save(rolePrivilege);
			}
			
		}catch (Exception e) {
			ExceptionUtils.logBizException(RoleInfoServiceImpl.class, e.getMessage());
		}
		
	}


	@Override
	public RoleInfo getRoleInfoByRoleId(String roleId) throws BizException {
		 
		return this.roleInfoDao.findById(roleId);
	}


	@Override
	public void updateRoleAndLimits(RoleInfo roleInfo) throws BizException {
		try{
			roleInfoDao.deleteRoleLimits(roleInfo);
			this.saveRoleLimit(roleInfo);
			roleInfoDao.update(roleInfo);
		}catch (Exception e) {
			ExceptionUtils.logBizException(RoleInfoServiceImpl.class, e.getMessage());
		}
		
	}

	@Override
	public boolean isExistRole(RoleInfo roleInfo) throws BizException {
	 
		 return this.roleInfoDao.findById(roleInfo.getRoleId())!=null;
	}

	@Override
	public List<RoleInfo> queryRoleInfoByLimitGroupId(String limitGroupId)
			throws BizException {
		return roleInfoDao.queryRoleInfoByLimitGroupId(limitGroupId);
	}

	
	
}
