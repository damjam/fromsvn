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
	 * ��ȡȨ�޵��Ӧ�����е���Դ
	 * @param privs
	 * @return
	 */
	public Map<String,List<PrivilegeResource>> getPrivResource(String[] privs);

	public Paginater getPrivilegePageList(Privilege privilege,Pager pager);
	
	/**
	 * ��ȡһ����ɫ��Ȩ����
	 * @param roleId
	 * @return
	 * @throws BizException
	 */
	public List<PrivilegeTreeNode> getRoleTreeByRole(String roleId) throws BizException;
	/**
	 * ���Ȩ����
	 * 
	 * @param��һ��Ȩ���б�
	 * @paramȨ����ID
	 * @return List(PrivilegeRoleTree)
	 */
	@SuppressWarnings("unchecked")
	public List getRoleTree(List treeList,String limitGroupId);
	
	/**
	 * ���Ȩ����
	 * 
	 * @param��һ��Ȩ���б�
	 * @paramȨ����ID
	 * @return List(PrivilegeRoleTree)
	 */
	@SuppressWarnings("unchecked")
	public List getRoleTree(List treeList);
	
	/**
	 * 
	 * ��ȡȨ�޵�
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
