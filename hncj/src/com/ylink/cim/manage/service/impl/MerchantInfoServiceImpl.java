package com.ylink.cim.manage.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ylink.cim.admin.domain.UserInfo;
import com.ylink.cim.admin.service.IdFactoryService;
import com.ylink.cim.manage.dao.MerchantInfoDao;
import com.ylink.cim.manage.domain.MerchantInfo;
import com.ylink.cim.manage.service.MerchantInfoService;

import flink.consant.Constants;
import flink.etc.BizException;
import flink.util.DateUtil;

@Component("merchantInfoService")
public class MerchantInfoServiceImpl implements MerchantInfoService {
	@Autowired
	private MerchantInfoDao merchantInfoDao;
	@Autowired
	private IdFactoryService idFactoryService;

	public void saveOrUpdate(MerchantInfo merchantInfo, UserInfo userInfo) throws BizException {
		if (StringUtils.isEmpty(merchantInfo.getId())) {
			merchantInfo.setId(idFactoryService.generateId(Constants.MERCHANT_INFO_ID));
			merchantInfo.setCreateDate(DateUtil.getCurrent());
			merchantInfo.setBranchNo(userInfo.getBranchNo());
			merchantInfoDao.save(merchantInfo);
		} else {
			merchantInfo.setUpdateDate(DateUtil.getCurrent());
			merchantInfoDao.update(merchantInfo);
		}

	}

	public void delete(String id) throws BizException {
		merchantInfoDao.deleteById(id);
	}

}
