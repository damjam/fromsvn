package com.ylink.cim.manage.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ylink.cim.admin.domain.UserInfo;
import com.ylink.cim.admin.service.IdFactoryService;
import com.ylink.cim.manage.dao.ContactDao;
import com.ylink.cim.manage.domain.Contact;
import com.ylink.cim.manage.service.ContactService;

import flink.consant.Constants;
import flink.etc.BizException;
import flink.util.DateUtil;

@Component("contactService")
public class ContactServiceImpl implements ContactService {

	@Autowired
	private ContactDao contactDao;
	@Autowired
	private IdFactoryService idFactoryService;
	@Override
	public void save(Contact contact, UserInfo userInfo) throws BizException{
		String id = idFactoryService.generateId(Constants.CONTACT_ID);
		contact.setId(id);
		contact.setCreateDate(DateUtil.getCurrent());
		contact.setBranchNo(userInfo.getBranchNo());
		contactDao.save(contact);
	}
	@Override
	public void delete(String id) throws BizException {
		contactDao.deleteById(id);
	}

	
}
