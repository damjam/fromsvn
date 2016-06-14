package com.ylink.cim.manage.service;

import java.util.List;
import java.util.Map;

import com.ylink.cim.admin.domain.UserInfo;
import com.ylink.cim.manage.domain.Contact;

import flink.etc.BizException;



public interface ContactService {

	void save(Contact contact, UserInfo userInfo) throws BizException;
	void delete(String id) throws BizException;
	void update(Contact contact) throws BizException;
	int addFromExcel(List<List<Map<String, Object>>> list, UserInfo sessionUser) throws BizException;
}
