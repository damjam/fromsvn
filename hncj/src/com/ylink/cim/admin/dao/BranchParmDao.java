package com.ylink.cim.admin.dao;

import java.util.Date;

import com.ylink.cim.admin.domain.BranchParm;

import flink.hibernate.BaseDao;
import flink.util.Pager;
import flink.util.Paginater;

/**
 * 
 * 
 *
 */
public interface BranchParmDao extends BaseDao {
	public void deleteBranchParmById(String id) throws Exception;

	public BranchParm findBranchParmById(String id) throws Exception;

	/**
	 * ��ȡ��ת���ƷѲ����������
	 * 
	 * @return
	 */
	public String generateFeeSettingIndex();

	/**
	 * ȡ���ݿ�ʱ��.
	 * 
	 * @return
	 */
	public Date getDbTime();

	public Paginater getPageList(Pager pager, BranchParm sysParm)
			throws Exception;

	/**
	 * ��ȡϵͳ��������ĳ��������Ϣ
	 * 
	 * */
	public BranchParm getBranchParmInfo(String code);

	/**
	 * �Ƿ�����Ѿ�����
	 * 
	 * @param code
	 * @return
	 */
	public boolean hasParm(String code) throws Exception;

	public void saveBranchParm(BranchParm sysParm) throws Exception;

	public void saveBranchParmInTransaction(BranchParm sysParm)
			throws Exception;

	public void updateBranchParm(BranchParm sysParm) throws Exception;

	/**
	 * �޸�ϵͳ��������Ϣ
	 * 
	 * */
	public void updBranchParm(BranchParm sysParm);
}
