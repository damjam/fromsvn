package com.ylink.cim.manage.service;

import com.ylink.cim.admin.domain.UserInfo;

import flink.etc.BizException;


public interface AccountJournalService {


	void add(String tradeType, Double amount, String billId, String remark, UserInfo userInfo) throws BizException;
	
	void deduct(String tradeType, Double amount, String billId, String remark, UserInfo userInfo) throws BizException;

	void reverse(String tradeType, String billId, String remark, UserInfo userInfo)throws BizException;
}
