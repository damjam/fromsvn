package com.ylink.cim.admin.service;

import java.util.List;

import com.ylink.cim.admin.domain.Privilege;
import com.ylink.cim.admin.domain.PrivilegeTreeNode;

import flink.etc.BizException;
import flink.util.Pager;
import flink.util.Paginater;

/**
 * х╗оч
 * 
 *
 */
public interface PrivilegeService {
 
	public Paginater getPrivilegePageList(Privilege privilege,Pager pager) throws BizException;
	
	public List<PrivilegeTreeNode> getRoleTreeByRole(String roleId)throws BizException;
	
	public List<PrivilegeTreeNode> getRoleTree(String limitGroupId)throws BizException;
	
	public List<PrivilegeTreeNode> getRoleTree()throws BizException;
	
	
	public void savePrivilege(Privilege privilege) throws BizException;
	
	public void deletePrivilege(Privilege privilege) throws BizException;
	
	public Privilege getPrivilege(String limitId) throws BizException;
	
	public List<Privilege> getPrivilegeList(Privilege privilege);
}
