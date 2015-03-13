package com.ylink.cim.manage.service;

import java.util.List;

import com.ylink.cim.manage.domain.OwnerInfo;
import com.ylink.cim.user.domain.UserInfo;

import flink.etc.BizException;

public interface OwnerInfoService {

	void delete(String id, UserInfo userInfo) throws BizException;

	void add(OwnerInfo houseInfo, UserInfo sessionUser) throws BizException;

	void update(OwnerInfo ownerInfo, UserInfo sessionUser) throws BizException;
	
	void cancel(String id, UserInfo sessionUser) throws BizException;

	void importOwnerInfo(List<OwnerInfo> list, UserInfo sessionUser) throws BizException;
	
}
