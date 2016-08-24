package com.ylink.cim.manage.service;

import java.util.List;
import java.util.Map;

import com.ylink.cim.admin.domain.UserInfo;
import com.ylink.cim.manage.domain.ParkingInfo;

import flink.etc.BizException;

public interface ParkingInfoService {

	void saveOrUpdate(ParkingInfo parkingInfo, UserInfo userInfo)
			throws BizException;

	void update(ParkingInfo parkingInfo, UserInfo userInfo) throws BizException;

	void delete(String id, UserInfo sessionUser) throws BizException;

	void save(ParkingInfo parkingInfo, UserInfo userInfo) throws BizException;

	Integer addFromExcel(List<List<Map<String, Object>>> list, UserInfo sessionUser) throws BizException;
}
