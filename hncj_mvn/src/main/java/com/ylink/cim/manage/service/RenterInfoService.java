package com.ylink.cim.manage.service;

import com.ylink.cim.admin.domain.UserInfo;
import com.ylink.cim.manage.domain.RenterInfo;

import flink.etc.BizException;

public interface RenterInfoService {

	void save(RenterInfo renterInfo, UserInfo userInfo) throws BizException;
	
	void update(RenterInfo renterInfo) throws BizException;

	void checkout(String id) throws BizException;

	void delete(String id) throws BizException;

}
