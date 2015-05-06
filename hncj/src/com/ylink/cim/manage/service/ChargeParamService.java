package com.ylink.cim.manage.service;

import com.ylink.cim.admin.domain.UserInfo;
import com.ylink.cim.manage.domain.ChargeItem;
import com.ylink.cim.manage.domain.ChargeParam;

import flink.etc.BizException;

public interface ChargeParamService {

	void saveParam(ChargeParam chargeParam, UserInfo userInfo) throws BizException;

	void updateParam(ChargeParam chargeParam, UserInfo userInfo) throws BizException;

	void saveOrUpdateItem(ChargeItem chargeItem, UserInfo userInfo) throws BizException;

	void deleteParam(String id, UserInfo sessionUser) throws BizException;

	void deleteItem(String id, UserInfo sessionUser) throws BizException;

	void deleteParamItem(String id, UserInfo sessionUser) throws BizException;

	void saveParamItem(String id, String[] itemIds, UserInfo userInfo) throws BizException;
}
