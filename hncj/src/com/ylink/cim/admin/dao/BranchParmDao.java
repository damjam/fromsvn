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
	 * 获取流转单计费参数设置序号
	 * 
	 * @return
	 */
	public String generateFeeSettingIndex();

	/**
	 * 取数据库时间.
	 * 
	 * @return
	 */
	public Date getDbTime();


	public Paginater getPageList(Pager pager, BranchParm sysParm) throws Exception;

	/**
	 * 获取系统参数表中某条数据信息
	 * 
	 * */
	public BranchParm getBranchParmInfo(String code);

	/**
	 * 是否参数已经存在
	 * 
	 * @param code
	 * @return
	 */
	public boolean hasParm(String code) throws Exception;


	public void saveBranchParm(BranchParm sysParm) throws Exception;

	public void saveBranchParmInTransaction(BranchParm sysParm) throws Exception;

	public void updateBranchParm(BranchParm sysParm) throws Exception;

	/**
	 * 修改系统参数表信息
	 * 
	 * */
	public void updBranchParm(BranchParm sysParm);
}
