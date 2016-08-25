package com.ylink.cim.admin.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ylink.cim.admin.dao.BranchParmDao;
import com.ylink.cim.admin.domain.BranchParam;
import com.ylink.cim.admin.service.BranchParmService;

import flink.etc.BizException;
import flink.util.Pager;
import flink.util.Paginater;

@Component("branchParmService")
public class BranchParmServiceImpl implements BranchParmService {

	private static Logger logger = Logger.getLogger(BranchParmServiceImpl.class);

	@Autowired
	private BranchParmDao branchParmDao;

	/**
	 * 根据主键删除记录
	 */
	@Override
	public void delete(String id) throws BizException {

		try {
			branchParmDao.deleteById(id);
		} catch (Exception e) {
			logger.debug(BranchParmServiceImpl.class.getClass() + "," + e.getMessage());
			throw new BizException(e.getMessage());
		}
	}

	/**
	 * 分页查询
	 * 
	 * @param pager
	 * @return
	 * @throws Exception
	 */
	@Override
	public Paginater findAll(Pager pager, BranchParam branchParm) throws BizException {

		Paginater paginater = null;
		try {
			paginater = branchParmDao.getPageList(pager, branchParm);
			return paginater;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BizException(e.getMessage());
		}

	}

	/**
	 * 传入code的编码即可,通过参数实例返回
	 * 
	 * @throws BizException
	 */

	@Override
	public BranchParam findByIdWithLock(String code) throws BizException {

		return this.branchParmDao.findByIdWithLock(code);
	}

	@Override
	public void save(BranchParam branchParm) throws BizException {
		try {
			if (branchParmDao.findById(branchParm.getCode()) != null) {
				throw new Exception("该数据已经存在!" + branchParm.getCode());
			}
			this.branchParmDao.save(branchParm);
		} catch (Exception e) {
			logger.debug(BranchParmServiceImpl.class.getClass() + "," + e.getMessage());
			throw new BizException(e.getMessage());
		}
	}

	@Override
	public void update(BranchParam branchParm) throws BizException {

		try {
			this.branchParmDao.update(branchParm);
		} catch (Exception e) {
			logger.debug(BranchParmServiceImpl.class.getClass() + "," + e.getMessage());
			throw new BizException(e.getMessage());
		}

	}

}
