package com.ylink.cim.manage.service;

import com.ylink.cim.manage.domain.Contact;
import com.ylink.cim.user.domain.UserInfo;

import flink.etc.BizException;



public interface ContactService {

	void save(Contact contact, UserInfo userInfo) throws BizException;
	void delete(String id) throws BizException;
}
