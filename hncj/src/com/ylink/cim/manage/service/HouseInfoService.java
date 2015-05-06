package com.ylink.cim.manage.service;

import com.ylink.cim.admin.domain.UserInfo;
import com.ylink.cim.manage.domain.HouseInfo;

import flink.etc.BizException;

public interface HouseInfoService {

	void delete(String id, UserInfo userInfo) throws BizException;

	void add(HouseInfo houseInfo, UserInfo sessionUser) throws BizException;
	
}
