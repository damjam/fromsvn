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
		storage.setId(IdFactoryHelper.getId(Storage.class));
		storageDao.save(storage);
	}

	@Override
	public void update(Storage storage) throws BizException {
		storageDao.update(storage);
	}

	@Override
	public void outstock(String id, Integer inoutNum, String orderId, String remark, UserInfo userInfo) throws BizException {
		if(StringUtils.isNotBlank(orderId)){
			OrderRecord orderRecord = orderRecordDao.findById(orderId);
			Assert.notNull(orderRecord, LogUtils.r("订单号{?}不存在", orderId));
			Assert.notEquals(OrderState.CANCELED.getValue(), orderRecord.getState(), "订单已取消，无法出库");
		}else {
			Assert.notEmpty(remark, "出库原因不能为空");
		}
		Assert.isTrue(inoutNum > 0, "入库数量必须为正整数");
		Storage storage = storageDao.findByIdWithLock(id);
		if(storage.getNum() < inoutNum){
			throw new BizException("库存不足");
		}
		int	num = storage.getNum() - inoutNum;
		storage.setNum(num);
		storageDao.update(storage);
		storageJournalService.add(id, inoutNum, "出库", num, orderId, remark, userInfo);
	}

	@Override
	public void instock(String id, Integer inoutNum, UserInfo userInfo) throws BizException {
		Storage storage = storageDao.findByIdWithLock(id);
		Assert.notNull(storage, "库存信息不存在");
		Assert.isTrue(inoutNum > 0, "入库数量必须为正整数");
		int num = storage.getNum() + inoutNum;
		storage.setNum(num);
		storageDao.update(storage);
		storageJournalService.add(id, inoutNum, "入库", num, null, null, userInfo);
	}
	
	
}
