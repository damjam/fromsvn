package com.ylink.cim.manage.service;

import com.ylink.cim.admin.domain.UserInfo;
import com.ylink.cim.manage.domain.Contact;

import flink.etc.BizException;



public interface ContactService {

	void save(Contact contact, UserInfo userInfo) throws BizException;
	void delete(String id) throws BizException;
}
