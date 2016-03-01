package com.ylink.cim.manage.service;

import com.ylink.cim.admin.domain.UserInfo;

public interface EmpTransferService {

	void addEmpTransfer(String empId, String transferType, String transferDetail, UserInfo userInfo);
}
