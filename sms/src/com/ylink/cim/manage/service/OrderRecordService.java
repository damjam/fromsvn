package com.ylink.cim.manage.service;

import com.ylink.cim.admin.domain.UserInfo;
import com.ylink.cim.manage.domain.OrderRecord;

import flink.etc.BizException;

public interface OrderRecordService {

	void save(OrderRecord model, UserInfo userInfo) throws BizException;
	
	void update(OrderRecord model, UserInfo userInfo) throws BizException;

	void delete(String id) throws BizException;

	void updateState(String id, String state) throws BizException;

	boolean canEdit(String id) throws BizException;

	void pay(String id, UserInfo sessionUser) throws BizException;

	void cancel(String id) throws BizException;

}
