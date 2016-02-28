package com.ylink.cim.manage.service;

import com.ylink.cim.admin.domain.UserInfo;
import com.ylink.cim.manage.domain.Repair;

import flink.etc.BizException;

public interface EmployeeService {

	void save(Repair model, UserInfo sessionUser) throws BizException;

}
