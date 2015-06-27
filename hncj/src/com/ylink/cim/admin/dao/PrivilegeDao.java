package com.ylink.cim.admin.dao;

import java.util.List;
import java.util.Map;

import com.ylink.cim.admin.domain.Privilege;
import com.ylink.cim.admin.domain.PrivilegeResource;
import com.ylink.cim.admin.domain.PrivilegeTreeNode;

import flink.etc.BizException;
import flink.hibernate.BaseDao;
import flink.util.Pager;
import flink.util.Paginater;

public interface PrivilegeDao extends BaseDao {

	String SERVICE_NAME = "privilegeDao";

	/**
	 * ��ȡȨ�޵��Ӧ�����е���Դ
	 * 
	 * @param privs
	 * @return
	 */
	public Map<String, List<PrivilegeResource>> getPrivResource(String[] privs);

	public Paginater getPrivilegePageList(Privilege privilege, Pager pager);

	/**
	 * ��ȡһ����ɫ��Ȩ����
	 * 
	 * @param roleId
	 * @return
	 * @throws BizException
	 */
	public List<PrivilegeTreeNode> getRoleTreeByRole(String roleId)
			throws BizException;

	/**
	 * ���Ȩ����
	 * 
	 * @param��һ��Ȩ���б�
	 * @paramȨ����ID
	 * @return List(PrivilegeRoleTree)
	 */
	public List<PrivilegeTreeNode> getRoleTree(String limitGroupId);

	/**
	 * ���Ȩ����
	 * 
	 * @param��һ��Ȩ���б�
	 * @paramȨ����ID
	 * @return List(PrivilegeRoleTree)
	 */
	public List getRoleTree();

	/**
	 * 
	 * ��ȡȨ�޵�
	 * 
	 * */
	public List<Map> getLimitGroupPrivilege(String limitGroupId);

	public List<Privilege> getPrivilegeList(Privilege privilege);

	public boolean hasSubPris(String limitId);

	public Paginater findPrivs(Privilege privilege, Pager pager);

	public Integer getNextIndex(String parent);
}
