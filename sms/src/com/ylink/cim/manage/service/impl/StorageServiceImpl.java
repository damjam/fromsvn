package com.ylink.cim.manage.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ylink.cim.admin.domain.UserInfo;
import com.ylink.cim.manage.dao.StorageDao;
import com.ylink.cim.manage.domain.Storage;
import com.ylink.cim.manage.service.StorageJournalService;
import com.ylink.cim.manage.service.StorageService;

import flink.IdFactoryHelper;
import flink.etc.Assert;
import flink.etc.BizException;

@Component("storageService")
public class StorageServiceImpl implements StorageService {

	@Autowired
	private StorageDao storageDao;
	@Autowired
	private StorageJournalService storageJournalService;

	@Override
	public void save(Storage storage, UserInfo userInfo) throws BizException {
		storage.setBranchNo(userInfo.getBranchNo());
		storage.setId(IdFactoryHelper.getId(Storage.class));
		storageDao.save(storage);
	}

	@Override
	public void update(Storage storage) throws BizException {
		storageDao.update(storage);
	}

	@Override
	public void outstock(String id, Integer inoutNum, UserInfo userInfo) throws BizException {
		Storage storage = storageDao.findByIdWithLock(id);
		if(storage.getNum() < inoutNum){
			throw new BizException("库存不足");
		}
		if(storage.getNum() == inoutNum){
			storageDao.delete(storage);
			return;
		}
		int num = storage.getNum() - inoutNum;
		storage.setNum(num);
		storageDao.update(storage);
		storageJournalService.add(id, inoutNum, "出库", num, userInfo);
	}

	@Override
	public void instock(String id, Integer inoutNum, UserInfo userInfo) throws BizException {
		Storage storage = storageDao.findByIdWithLock(id);
		Assert.notNull(storage, "库存信息不存在");
		int num = storage.getNum() + inoutNum;
		storage.setNum(num);
		storageDao.update(storage);
		storageJournalService.add(id, inoutNum, "入库", num, userInfo);
	}
	
	
}
