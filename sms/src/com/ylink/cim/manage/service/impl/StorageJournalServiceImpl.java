package com.ylink.cim.manage.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ylink.cim.admin.domain.UserInfo;
import com.ylink.cim.manage.dao.StorageDao;
import com.ylink.cim.manage.dao.StorageJournalDao;
import com.ylink.cim.manage.domain.Storage;
import com.ylink.cim.manage.domain.StorageJournal;
import com.ylink.cim.manage.service.StorageJournalService;

import flink.IdFactoryHelper;
import flink.etc.BizException;
import flink.util.DateUtil;
@Component("storageJournalService")
public class StorageJournalServiceImpl implements StorageJournalService {

	@Autowired
	private StorageJournalDao storageJournalDao;
	@Autowired
	private StorageDao storageDao;
	
	@Override
	public void save(StorageJournal storageJournal, UserInfo userInfo) throws BizException {
		storageJournal.setBranchNo(userInfo.getBranchNo());
		storageJournal.setId(IdFactoryHelper.getId(StorageJournal.class));
		storageJournalDao.save(storageJournal);
	}

	@Override
	public void update(StorageJournal storageJournal) throws BizException {
		storageJournalDao.update(storageJournal);
	}

	@Override
	public void add(String id, Integer inoutNum, String inoutType, int num, String orderId, String remark, UserInfo userInfo) {
		Storage storage = storageDao.findById(id);
		StorageJournal journal = new StorageJournal();
		journal.setBranchNo(userInfo.getBranchNo());
		journal.setCreateUser(userInfo.getUserName());
		journal.setId(IdFactoryHelper.getId(StorageJournal.class));
		journal.setCarModel(storage.getCarModel());
		journal.setColor(storage.getColor());
		journal.setCreateDate(DateUtil.getCurrent());
		journal.setInoutType(inoutType);
		journal.setMaterial(storage.getMaterial());
		journal.setNum(num);
		journal.setInoutNum(inoutNum);
		journal.setInoutType(inoutType);
		journal.setProduct(storage.getProduct());
		journal.setProductType(storage.getProductType());
		journal.setShelf(storage.getShelf());
		journal.setOrderId(orderId);
		journal.setRemark(remark);
		storageJournalDao.save(journal);
	}
	
}
