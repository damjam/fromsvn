package com.ylink.cim.manage.service;

import com.ylink.cim.admin.domain.UserInfo;
import com.ylink.cim.manage.domain.CarInfo;

import flink.etc.BizException;

public interface CarInfoService {

	void saveOrUpdate(CarInfo carInfo, UserInfo userInfo) throws BizException;

	void update(CarInfo carInfo, UserInfo userInfo) throws BizException;

	void delete(String id, UserInfo sessionUser) throws BizException;

	void save(CarInfo carInfo, UserInfo userInfo) throws BizException;
}
