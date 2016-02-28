package com.ylink.cim.manage.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ylink.cim.admin.domain.UserInfo;
import com.ylink.cim.manage.dao.EmployeeDao;
import com.ylink.cim.manage.domain.Repair;
import com.ylink.cim.manage.service.EmployeeService;

import flink.etc.BizException;
import flink.util.DateUtil;
@Component("employeeService")
public class EmployeeServiceImpl implements EmployeeService {
	@Autowired
	private EmployeeDao employeeDao;

	@Override
	public void save(Repair model, UserInfo sessionUser) throws BizException{
		model.setCreateDate(DateUtil.getCurrent());
		model.setCreateUser(sessionUser.getUserName());
		model.setBranchNo(sessionUser.getBranchNo());
		employeeDao.save(model);
	}
}
