package com.ylink.cim.manage.service;

import com.ylink.cim.admin.domain.UserInfo;
import com.ylink.cim.manage.domain.ParkingInfo;

import flink.etc.BizException;

public interface ParkingInfoService {

	void saveOrUpdate(ParkingInfo parkingInfo, UserInfo userInfo)
			throws BizException;

	void update(ParkingInfo parkingInfo, UserInfo userInfo) throws BizException;

	void delete(String id, UserInfo sessionUser) throws BizException;

	void save(ParkingInfo parkingInfo, UserInfo userInfo) throws BizException;
}
