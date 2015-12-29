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
	 * ��������ɾ����¼
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
	 * ��ҳ��ѯ
	 * 
	 * @param pager
	 * @return
	 * @throws Exception
	 */
	@Override
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
	 * ����code�ı��뼴��,ͨ������ʵ������
	 * 
	 * @throws BizException
	 */

	@Override
	public BranchParm findByIdWithLock(String code) throws BizException {

		return this.branchParmDao.findByIdWithLock(code);
	}

	@Override
	public void save(BranchParm branchParm) throws BizException {
		try {
			if (branchParmDao.findById(branchParm.getCode()) != null) {
				throw new Exception("�������Ѿ�����!" + branchParm.getCode());
			}
			this.branchParmDao.save(branchParm);
		} catch (Exception e) {
			logger.debug(BranchParmServiceImpl.class.getClass() + "," + e.getMessage());
			throw new BizException(e.getMessage());
		}
	}

	@Override
	public void update(BranchParm branchParm) throws BizException {

		try {
			this.branchParmDao.update(branchParm);
		} catch (Exception e) {
			logger.debug(BranchParmServiceImpl.class.getClass() + "," + e.getMessage());
			throw new BizException(e.getMessage());
		}

	}

}
