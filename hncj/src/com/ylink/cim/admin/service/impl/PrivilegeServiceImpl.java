package com.ylink.cim.admin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ylink.cim.admin.dao.PrivilegeDao;
import com.ylink.cim.admin.domain.Privilege;
import com.ylink.cim.admin.domain.PrivilegeTreeNode;
import com.ylink.cim.admin.service.PrivilegeService;

import flink.etc.BizException;
import flink.util.Pager;
import flink.util.Paginater;
@Component("privilegeService")
public class PrivilegeServiceImpl implements PrivilegeService {

	@Autowired
	private PrivilegeDao privilegeDao;

	public void setPrivilegeDao(PrivilegeDao privilegeDao) {
		this.privilegeDao = privilegeDao;
	}

	public Paginater getPrivilegePageList(Privilege privilege, Pager pager)throws  BizException{
		return this.privilegeDao.getPrivilegePageList(privilege, pager);
	}
	

	public List<PrivilegeTreeNode> getRoleTreeByRole(String roleId) throws BizException {
		return this.privilegeDao.getRoleTreeByRole(roleId);
	}

	/* (non-Javadoc)
	 * @see gnete.dsf.biz.PrivilegeService#getRoleTree()
	 */
	public List getRoleTree(String limitGroupId) throws BizException {
		
		List<PrivilegeTreeNode> list = new ArrayList<PrivilegeTreeNode>();
		/*
		//初始化第一个节点
		PrivilegeTreeNode node = new PrivilegeTreeNode();
		node.setCode(PrivilegeTreeNode.ROOT);
		node.setName("权限菜单");
		node.setParentCode(PrivilegeTreeNode.ROOT_PARENT);
		list.add(node);
		*/
		return privilegeDao.getRoleTree(list,limitGroupId);
	}

	/* (non-Javadoc)
	 * @see gnete.dsf.biz.PrivilegeService#getRoleTree()
	 */
	public List<PrivilegeTreeNode> getRoleTree() throws BizException {
		
		List<PrivilegeTreeNode> list = new ArrayList<PrivilegeTreeNode>();
		
		/*
		//初始化第一个节点
		PrivilegeTreeNode node = new PrivilegeTreeNode();
		node.setCode(PrivilegeTreeNode.ROOT);
		node.setName("权限菜单");
		node.setParentCode(PrivilegeTreeNode.ROOT_PARENT);
		list.add(node);
		*/
		return privilegeDao.getRoleTree(list);
	}

	public void savePrivilege(Privilege privilege) throws BizException {
		
		if(null!=this.privilegeDao.findById(privilege.getLimitId())){
			throw new BizException("权限点  "+privilege.getLimitId()+",已经存在");
		}
		
		this.privilegeDao.save(privilege);
		
	}

	public void deletePrivilege(Privilege privilege) throws BizException {
		this.privilegeDao.deleteById(privilege.getLimitId());
	}

	public Privilege getPrivilege(String limitId) throws BizException {
		 
		return this.privilegeDao.findById(limitId);
	}

	public List<Privilege> getPrivilegeList(Privilege privilege) {

		return this.privilegeDao.getPrivilegeList(privilege);
	}

	
	
}
