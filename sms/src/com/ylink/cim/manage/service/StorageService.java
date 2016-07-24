package com.ylink.cim.manage.service;

import com.ylink.cim.admin.domain.UserInfo;
import com.ylink.cim.manage.domain.Storage;

import flink.etc.BizException;

public interface StorageService {

	
	void save(Storage storage, UserInfo userInfo) throws BizException;
	
	void update(Storage storage) throws BizException;

	void outstock(String id, Integer inoutNum, UserInfo userInfo) throws BizException;

	void instock(String id, Integer inoutNum, UserInfo userInfo) throws BizException;
}
