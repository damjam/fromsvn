package com.ylink.cim.admin.dao;

import java.util.List;
import java.util.Map;

import com.ylink.cim.admin.domain.Privilege;
import com.ylink.cim.admin.domain.PrivilegeResource;
import com.ylink.cim.admin.domain.PrivilegeTreeNode;
import com.ylink.cim.admin.view.PrivilegeActionForm;

import flink.etc.BizException;
import flink.hibernate.BaseDao;
import flink.util.Pager;
import flink.util.Paginater;


public interface PrivilegeDao extends BaseDao {

	String SERVICE_NAME = "privilegeDao";
	/**
	 * 获取权限点对应的所有的资源
	 * @param privs
	 * @return
	 */
	public Map<String,List<PrivilegeResource>> getPrivResource(String[] privs);

	public Paginater getPrivilegePageList(Privilege privilege,Pager pager);
	
	/**
	 * 获取一个角色的权限树
	 * @param roleId
	 * @return
	 * @throws BizException
	 */
	public List<PrivilegeTreeNode> getRoleTreeByRole(String roleId) throws BizException;
	/**
	 * 获得权限树
	 * 
	 * @param第一个权限列表
	 * @param权限组ID
	 * @return List(PrivilegeRoleTree)
	 */
	@SuppressWarnings("unchecked")
	public List getRoleTree(List treeList,String limitGroupId);
	
	/**
	 * 获得权限树
	 * 
	 * @param第一个权限列表
	 * @param权限组ID
	 * @return List(PrivilegeRoleTree)
	 */
	@SuppressWarnings("unchecked")
	public List getRoleTree(List treeList);
	
	/**
	 * 
	 * 获取权限点
	 * 
	 * */
	public List<Map> getLimitGroupPrivilege(String limitGroupId);
	
	public List<Privilege> getPrivilegeList(Privilege privilege);
	
	public boolean existPriRes(String limitId, Long id);
	
	public boolean hasSubPris(String limitId);
	
	public Paginater findPrivs(PrivilegeActionForm privilegeActionForm, Pager pager);
	
	public Paginater findPrivRes(PrivilegeActionForm privilegeActionForm, Pager pager);
	
	public Integer getNextIndex(String parent);
}
