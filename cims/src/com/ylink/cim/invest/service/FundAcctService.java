package com.ylink.cim.invest.service;

import java.util.Map;

import com.ylink.cim.common.msg.handle.MsgField;

import flink.etc.BizException;

public interface FundAcctService {

	public String addWithdraw(Map<String, String> map) throws BizException;

	public String addDeposit(Map<String, String> map) throws BizException;

	public Map<String, String> queryFundAccts(Map<String, String> map, String[] investAcctNo) throws BizException;

	public Map<String, MsgField> queryFundAcct(Map<String, String> map, String investAcctNo) throws BizException;

	public String doOneDeal(Map<String, String> map) throws BizException;

	public Map<String, Map<String, MsgField>> queryAcctsNeedPay(Map<String, String> map, String[] investAcctNos)
			throws BizException;

	public Map<String, MsgField> queryOneAcctNeedPay(Map<String, String> map, String investAcctNo) throws BizException;

	public String payDebt(Map<String, String> map) throws BizException;
}
