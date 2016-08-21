package com.ylink.cim.manage.service;

import com.ylink.cim.admin.domain.UserInfo;
import com.ylink.cim.manage.domain.StorageJournal;

import flink.etc.BizException;

public interface StorageJournalService {

	
	void save(StorageJournal storageJournal, UserInfo userInfo) throws BizException;
	
	void update(StorageJournal storageJournal) throws BizException;

	void add(String id, Integer inoutNum, String inoutType, int num, String orderId, String remark, UserInfo userInfo);

}
