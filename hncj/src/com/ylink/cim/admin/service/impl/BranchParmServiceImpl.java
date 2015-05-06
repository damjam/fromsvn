package com.ylink.cim.admin.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ylink.cim.admin.dao.BranchParmDao;
import com.ylink.cim.admin.domain.BranchParm;
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
	public void delete(String id) throws BizException {

		try {
			if (!branchParmDao.hasParm(id)) {
				throw new Exception("不存在要删除的记录!");
			}
			this.branchParmDao.deleteBranchParmById(id);
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
	public Paginater findAll(Pager pager, BranchParm branchParm) throws BizException {

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
	public BranchParm findById(String id) throws BizException {

		BranchParm branchParm;
		try {
			branchParm = (BranchParm) this.branchParmDao.findBranchParmById(id);
			return branchParm;
		} catch (Exception e) {
			throw new BizException(e.getMessage());
		}

	}

	public BranchParm findByIdWithLock(String code) throws BizException {

		return this.branchParmDao.findByIdWithLock(code);
	}

	public void save(BranchParm branchParm) throws BizException {
		try {
			if (branchParmDao.hasParm(branchParm.getCode())) {
				throw new Exception("该数据已经存在!" + branchParm.getCode());
			}

			this.branchParmDao.saveBranchParm(branchParm);
		} catch (Exception e) {
			logger.debug(BranchParmServiceImpl.class.getClass() + "," + e.getMessage());
			throw new BizException(e.getMessage());
		}
	}

	public void update(BranchParm branchParm) throws BizException {

		try {
			this.branchParmDao.update(branchParm);
		} catch (Exception e) {
			logger.debug(BranchParmServiceImpl.class.getClass() + "," + e.getMessage());
			throw new BizException(e.getMessage());
		}

	}

}
