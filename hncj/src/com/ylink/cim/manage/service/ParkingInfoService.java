package com.ylink.cim.manage.service;

import com.ylink.cim.manage.domain.ParkingInfo;
import com.ylink.cim.user.domain.UserInfo;

import flink.etc.BizException;


public interface ParkingInfoService {


	void saveOrUpdate(ParkingInfo parkingInfo, UserInfo userInfo) throws BizException;
	void update(ParkingInfo parkingInfo, UserInfo userInfo) throws BizException;
	void delete(String id, UserInfo sessionUser) throws BizException;
	void save(ParkingInfo parkingInfo, UserInfo userInfo) throws BizException;
}
