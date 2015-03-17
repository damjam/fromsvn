package com.ylink.cim.manage.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ylink.cim.manage.dao.MerchantInfoDao;
import com.ylink.cim.manage.domain.MerchantInfo;
import com.ylink.cim.manage.service.MerchantInfoService;
import com.ylink.cim.user.domain.UserInfo;

import flink.etc.BizException;
import flink.util.DateUtil;
@Component("merchantInfoService")
public class MerchantInfoServiceImpl implements MerchantInfoService {
	@Autowired
	private MerchantInfoDao merchantInfoDao;
	public void saveMerchant(MerchantInfo merchantInfo, UserInfo userInfo) throws BizException {
		merchantInfo.setCreateDate(DateUtil.getCurrent());
		userInfo.setBranchNo(userInfo.getBranchNo());
		merchantInfoDao.save(merchantInfo);
	}

}
