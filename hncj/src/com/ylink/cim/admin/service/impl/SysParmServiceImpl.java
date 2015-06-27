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
	 * ��������ɾ����¼
	 */
	@Override
	public void delete(String id) throws BizException {

		try {
			if (!sysParmDao.hasParm(id)) {
				throw new Exception("������Ҫɾ���ļ�¼!");
			}
			this.sysParmDao.deleteSysParmById(id);
		} catch (Exception e) {
			logger.debug(SysParmServiceImpl.class.getClass() + "," + e.getMessage());
			throw new BizException(e.getMessage());
		}
	}

	/**
	 * ��ҳ��ѯ
	 * 
	 * @param pager
	 * @return
	 * @throws Exception
	 */
	@Override
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
	 * ����code�ı��뼴��,ͨ������ʵ������
	 * 
	 * @throws BizException
	 */
	@Override
	public SysParm findById(String id) throws BizException {

		SysParm sysParm;
		try {
			sysParm = this.sysParmDao.findSysParmById(id);
			return sysParm;
		} catch (Exception e) {
			throw new BizException(e.getMessage());
		}

	}

	@Override
	public SysParm findByIdWithLock(String code) throws BizException {

		return this.sysParmDao.findByIdWithLock(code);
	}



	@Override
	public void save(SysParm sysParm) throws BizException {
		try {
			if (sysParmDao.hasParm(sysParm.getCode())) {
				throw new Exception("�������Ѿ�����!"+sysParm.getCode());
			}

			this.sysParmDao.saveSysParm(sysParm);
		} catch (Exception e) {
			logger.debug(SysParmServiceImpl.class.getClass() + "," + e.getMessage());
			throw new BizException(e.getMessage());
		}
	}

	@Override
	public void update(SysParm sysParm) throws BizException {

		try {
			this.sysParmDao.update(sysParm);
		} catch (Exception e) {
			logger.debug(SysParmServiceImpl.class.getClass() + "," + e.getMessage());
			throw new BizException(e.getMessage());
		}

	}

}
