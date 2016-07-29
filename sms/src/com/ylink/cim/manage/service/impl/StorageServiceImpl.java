package com.ylink.cim.manage.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ylink.cim.admin.domain.UserInfo;
import com.ylink.cim.common.state.OrderState;
import com.ylink.cim.manage.dao.OrderRecordDao;
import com.ylink.cim.manage.dao.StorageDao;
import com.ylink.cim.manage.domain.OrderRecord;
import com.ylink.cim.manage.domain.Storage;
import com.ylink.cim.manage.service.StorageJournalService;
import com.ylink.cim.manage.service.StorageService;

import flink.IdFactoryHelper;
import flink.etc.Assert;
import flink.etc.BizException;
import flink.util.LogUtils;

@Component("storageService")
public class StorageServiceImpl implements StorageService {

	@Autowired
	private StorageDao storageDao;
	@Autowired
	private StorageJournalService storageJournalService;
	@Autowired
	private OrderRecordDao orderRecordDao;
	
	@Override
	public void save(Storage storage, UserInfo userInfo) throws BizException {
		storage.setBranchNo(userInfo.getBranchNo());
		String id = IdFactoryHelper.getId(Storage.class);
		storage.setId(id);
		storageDao.save(storage);
		storageJournalService.add(id, storage.getNum(), "���", storage.getNum(), null, null, userInfo);
	}

	@Override
	public void update(Storage storage) throws BizException {
		storageDao.update(storage);
	}

	@Override
	public void outstock(String id, Integer inoutNum, String orderId, String remark, UserInfo userInfo) throws BizException {
		if(StringUtils.isNotBlank(orderId)){
			OrderRecord orderRecord = orderRecordDao.findById(orderId);
			Assert.notNull(orderRecord, LogUtils.r("������{?}������", orderId));
			Assert.notEquals(OrderState.CANCELED.getValue(), orderRecord.getState(), "������ȡ�����޷�����");
		}else {
			Assert.notEmpty(remark, "����ԭ����Ϊ��");
		}
		Assert.isTrue(inoutNum > 0, "�����������Ϊ������");
		Storage storage = storageDao.findByIdWithLock(id);
		if(storage.getNum() < inoutNum){
			throw new BizException("��治��");
		}
		int	num = storage.getNum() - inoutNum;
		storage.setNum(num);
		storageDao.update(storage);
		storageJournalService.add(id, inoutNum, "����", num, orderId, remark, userInfo);
	}

	@Override
	public void instock(String id, Integer inoutNum, UserInfo userInfo) throws BizException {
		Storage storage = storageDao.findByIdWithLock(id);
		Assert.notNull(storage, "�����Ϣ������");
		Assert.isTrue(inoutNum > 0, "�����������Ϊ������");
		int num = storage.getNum() + inoutNum;
		storage.setNum(num);
		storageDao.update(storage);
		storageJournalService.add(id, inoutNum, "���", num, null, null, userInfo);
	}
	
	
}
