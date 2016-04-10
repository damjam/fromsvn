package com.ylink.cim.manage.service;

import java.util.List;
import java.util.Map;

import com.ylink.cim.admin.domain.UserInfo;
import com.ylink.cim.manage.domain.HouseInfo;

import flink.etc.BizException;

public interface HouseInfoService {

	void delete(String id, UserInfo userInfo) throws BizException;

	void add(HouseInfo houseInfo, UserInfo sessionUser) throws BizException;

	void addFromExcel(List<List<Map<String, Object>>> list, UserInfo userInfo) throws BizException;

}
