package com.ylink.cim.manage.service;

import java.util.List;

import com.ylink.cim.admin.domain.UserInfo;
import com.ylink.cim.manage.domain.WaterRecord;

import flink.etc.BizException;

public interface WaterRecordService {

	void saveWaterRecord(WaterRecord waterRecord, UserInfo userInfo) throws BizException;
	Integer checkAllRecord(UserInfo userInfo) throws BizException;
	boolean checkRecord(String id, UserInfo userInfo) throws BizException;
	void deleteRecord(String id, UserInfo userInfo) throws BizException;
	WaterRecord getPreRecord(String houseSn) throws BizException;
	void importDeposit(List<WaterRecord> list, UserInfo sessionUser) throws BizException;
}
