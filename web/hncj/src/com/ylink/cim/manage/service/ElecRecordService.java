package com.ylink.cim.manage.service;

import java.util.List;

import com.ylink.cim.admin.domain.UserInfo;
import com.ylink.cim.manage.domain.ElecRecord;

import flink.etc.BizException;

public interface ElecRecordService {

	void saveElecRecord(ElecRecord elecRecord, UserInfo userInfo) throws BizException;

	Integer checkAllRecord(UserInfo userInfo) throws BizException;

	boolean checkRecord(String id, UserInfo userInfo) throws BizException;

	void deleteRecord(String id, UserInfo userInfo) throws BizException;

	ElecRecord getPreRecord(String houseSn) throws BizException;

	void importDeposit(List<ElecRecord> list, UserInfo sessionUser) throws BizException;
}
