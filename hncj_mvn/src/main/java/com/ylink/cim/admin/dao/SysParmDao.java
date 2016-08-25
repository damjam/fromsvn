package com.ylink.cim.admin.dao;

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
	 * 获取流转单计费参数设置序号
	 * 
	 * @return
	 */
	public String generateFeeSettingIndex();



	public Paginater getPageList(Pager pager, SysParm sysParm) throws Exception;

	/**
	 * 获取系统参数表中某条数据信息
	 * 
	 * */
	public SysParm getSysParmInfo(String code);

	/**
	 * 是否参数已经存在
	 * 
	 * @param code
	 * @return
	 */
	public boolean hasParm(String code) throws Exception;


	public void saveSysParm(SysParm sysParm) throws Exception;

	public void saveSysParmInTransaction(SysParm sysParm) throws Exception;

	public void updateSysParm(SysParm sysParm) throws Exception;

	/**
	 * 修改系统参数表信息
	 * 
	 * */
	public void updSysParm(SysParm sysParm);
}
