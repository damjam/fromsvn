package com.ylink.cim.admin.dao;

import java.util.Date;

import com.ylink.cim.admin.domain.SysParm;

import flink.hibernate.BaseDao;
import flink.util.Pager;
import flink.util.Paginater;

/**
 * 
 * 
 *
 */
public interface SysParmDao extends BaseDao {
	public void deleteSysParmById(String id) throws Exception;

	

	public SysParm findSysParmById(String id) throws Exception;

	/**
	 * ��ȡ��ת���ƷѲ����������
	 * 
	 * @return
	 */
	public String generateFeeSettingIndex();



	public Paginater getPageList(Pager pager, SysParm sysParm) throws Exception;

	/**
	 * ��ȡϵͳ��������ĳ��������Ϣ
	 * 
	 * */
	public SysParm getSysParmInfo(String code);

	/**
	 * �Ƿ�����Ѿ�����
	 * 
	 * @param code
	 * @return
	 */
	public boolean hasParm(String code) throws Exception;


	public void saveSysParm(SysParm sysParm) throws Exception;

	public void saveSysParmInTransaction(SysParm sysParm) throws Exception;

	public void updateSysParm(SysParm sysParm) throws Exception;

	/**
	 * �޸�ϵͳ��������Ϣ
	 * 
	 * */
	public void updSysParm(SysParm sysParm);
}
