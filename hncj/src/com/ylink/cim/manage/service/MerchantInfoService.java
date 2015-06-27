package com.ylink.cim.manage.service;

import com.ylink.cim.admin.domain.UserInfo;
import com.ylink.cim.manage.domain.MerchantInfo;

import flink.etc.BizException;

public interface MerchantInfoService {

	void saveOrUpdate(MerchantInfo merchantInfo, UserInfo userInfo)
			throws BizException;

	void delete(String id) throws BizException;
}
