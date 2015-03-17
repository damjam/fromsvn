package com.ylink.cim.manage.service;

import com.ylink.cim.manage.domain.MerchantInfo;
import com.ylink.cim.user.domain.UserInfo;

import flink.etc.BizException;

public interface MerchantInfoService {

	void saveMerchant(MerchantInfo merchantInfo, UserInfo userInfo) throws BizException;
	
}
