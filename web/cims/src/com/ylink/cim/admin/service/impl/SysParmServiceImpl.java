package com.ylink.cim.admin.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ylink.cim.admin.dao.SysParmDao;
import com.ylink.cim.admin.domain.SysParm;
import com.ylink.cim.admin.service.SysParmService;

import flink.etc.BizException;
import flink.util.Pager;
import flink.util.Paginater;

@Component("sysParmService")
public class SysParmServiceImpl implements SysParmService {

	private static Logger logger = Logger.getLogger(SysParmServiceImpl.class);

	@Autowired
	private SysParmDao sysParmDao;

	/**
	 * 根据主键删除记录
	 */
	public void delete(String id) throws BizException {

		try {
			if (!sysParmDao.hasParm(id)) {
				throw new Exception("不存在要删除的记录!");
			}
			this.sysParmDao.deleteSysParmById(id);
		} catch (Exception e) {
			logger.debug(SysParmServiceImpl.class.getClass() + "," + e.getMessage());
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
	public Paginater findAll(Pager pager, SysParm sysParm) throws BizException {

		Paginater paginater = null;
		try {
			paginater = sysParmDao.getPageList(pager, sysParm);
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
	public SysParm findById(String id) throws BizException {

		SysParm sysParm;
		try {
			sysParm = this.sysParmDao.findSysParmById(id);
			return sysParm;
		} catch (Exception e) {
			throw new BizException(e.getMessage());
		}

	}

	public SysParm findByIdWithLock(String code) throws BizException {

		return this.sysParmDao.findByIdWithLock(code);
	}

	public void save(SysParm sysParm) throws BizException {
		try {
			if (sysParmDao.hasParm(sysParm.getCode())) {
				throw new Exception("该数据已经存在!" + sysParm.getCode());
			}

			this.sysParmDao.saveSysParm(sysParm);
		} catch (Exception e) {
			logger.debug(SysParmServiceImpl.class.getClass() + "," + e.getMessage());
			throw new BizException(e.getMessage());
		}
	}

	public void update(SysParm sysParm) throws BizException {

		try {
			this.sysParmDao.update(sysParm);
		} catch (Exception e) {
			logger.debug(SysParmServiceImpl.class.getClass() + "," + e.getMessage());
			throw new BizException(e.getMessage());
		}

	}

}
