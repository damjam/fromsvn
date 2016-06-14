package com.ylink.cim.manage.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ylink.cim.admin.domain.UserInfo;
import com.ylink.cim.admin.service.IdFactoryService;
import com.ylink.cim.manage.dao.ContactDao;
import com.ylink.cim.manage.domain.Contact;
import com.ylink.cim.manage.service.ContactService;

import flink.IdFactoryHelper;
import flink.consant.Constants;
import flink.etc.Assert;
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
	@Override
	public void update(Contact contact) throws BizException {
		contactDao.update(contact);
	}
	@Override
	public int addFromExcel(List<List<Map<String, Object>>> list, UserInfo sessionUser) throws BizException {
		Integer totalCnt = 0;
		for (int i = 0; i < list.size(); i++) {
			List<Map<String, Object>> rows = list.get(i);
			for (int j = 0; j < rows.size(); j++) {
				Map<String, Object> map = rows.get(j);
				Contact info = new Contact();
				String mobile = MapUtils.getString(map, "mobile");
				Map<String, Object> params = new HashMap<>();
				params.put("mobile", mobile);
				Assert.isEmpty(contactDao.findList(params), "手机号为"+mobile+"已存在");
				try{
					BeanUtils.populate(info, map);
				}catch (Exception e){
					throw new BizException("程序出现异常:"+e.getMessage());
				}
				String remark = MapUtils.getString(map, "remark");
				info.setRemark(remark);
				info.setCreateDate(DateUtil.getCurrent());
				info.setBranchNo(sessionUser.getBranchNo());
				info.setId(IdFactoryHelper.getId(Contact.class));
				contactDao.save(info);
				totalCnt++;
			}
		}
		return totalCnt;
	}

	
}
