package com.ylink.cim.manage.service;

import com.ylink.cim.admin.domain.UserInfo;
import com.ylink.cim.manage.domain.GeneralBill;

import flink.etc.BizException;

public interface BillService {


	void chargeGeneralBill(String id, UserInfo userInfo) throws BizException;


	void deleteBill(Class clazz, String id, UserInfo userInfo) throws BizException;

	void saveGeneralBill(GeneralBill generalBill, UserInfo userInfo) throws BizException;
}
