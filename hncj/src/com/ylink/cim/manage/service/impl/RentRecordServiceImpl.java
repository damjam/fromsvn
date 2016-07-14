package com.ylink.cim.manage.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ylink.cim.admin.domain.UserInfo;
import com.ylink.cim.manage.dao.RentRecordDao;
import com.ylink.cim.manage.domain.RentRecord;
import com.ylink.cim.manage.service.RentRecordService;

import flink.util.DateUtil;
@Component("rentRecordService")
public class RentRecordServiceImpl implements RentRecordService {

	@Autowired
	private RentRecordDao rentRecordDao;

	@Override
	public void save(RentRecord rentRecord, UserInfo userInfo) {
		rentRecord.setBranchNo(userInfo.getBranchNo());
		rentRecord.setCreateDate(DateUtil.getCurrent());
		rentRecordDao.save(rentRecord);
	}

	@Override
	public void update(RentRecord rentRecord) {
		rentRecordDao.update(rentRecord);
	}
	
}
