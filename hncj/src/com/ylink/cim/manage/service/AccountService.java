package com.ylink.cim.manage.service;

import com.ylink.cim.admin.domain.UserInfo;
import com.ylink.cim.manage.domain.Account;

import flink.etc.BizException;

public interface AccountService {

	void add(String houseSn, UserInfo sessionUser) throws BizException;

	Double deposit(String no, Double amount, UserInfo sessionUser)
			throws BizException;

	void withdraw(String no, Double amount, UserInfo sessionUser)
			throws BizException;

	void payBill(String no, Double amount, String billId, String remark,
			UserInfo sessionUser) throws BizException;

	String addAccountDetail(Account account, Double amount, String type,
			String inoutType, String billId, String remark, UserInfo userInfo)
			throws BizException;
}
