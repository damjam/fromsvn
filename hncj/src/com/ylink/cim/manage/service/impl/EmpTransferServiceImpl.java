package com.ylink.cim.manage.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ylink.cim.admin.domain.UserInfo;
import com.ylink.cim.manage.dao.EmpTransferDao;
import com.ylink.cim.manage.domain.EmpTransfer;
import com.ylink.cim.manage.service.EmpTransferService;

import flink.util.DateUtil;
@Component("empTransferService")
public class EmpTransferServiceImpl implements EmpTransferService {

	@Autowired
	private EmpTransferDao empTransferDao;

	@Override
	public void addEmpTransfer(String empId, String transferType, String transferDetail, UserInfo userInfo) {
		EmpTransfer empTransfer = new EmpTransfer();
		empTransfer.setCreateDate(DateUtil.getCurrent());
		empTransfer.setCreateUser(userInfo.getUserName());
		empTransfer.setEmpId(empId);
		empTransfer.setTransferType(transferType);
		empTransfer.setTransferDetail(transferDetail);
		empTransferDao.save(empTransfer);
	}
	
}
