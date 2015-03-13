package com.ylink.cim.invest.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ylink.cim.common.msg.handle.MsgA1014;
import com.ylink.cim.common.msg.handle.MsgA3001;
import com.ylink.cim.common.msg.handle.MsgA3002;
import com.ylink.cim.common.msg.handle.MsgField;
import com.ylink.cim.common.msg.handle.MsgUtil;
import com.ylink.cim.invest.service.FundAcctService;

import flink.etc.BizException;

@Component("fundAcctService")
public class FundAcctServiceImpl implements FundAcctService {

	//A3001
	public String addWithdraw(Map<String, String> map) throws BizException{
		map.put(MsgField.h_exch_code.getFieldCode(), "A3001");
		Map<String, MsgField> resMap = MsgUtil.getResBody(map, MsgA3001.A3001REQ, MsgA3001.A3001RES);
		return resMap.get(MsgField.order_no.getFieldCode()).getValue();
	}

	public String addDeposit(Map<String, String> map) throws BizException {
		map.put(MsgField.h_exch_code.getFieldCode(), "A3001");
		Map<String, MsgField> resMap = MsgUtil.getResBody(map, MsgA3001.A3001REQ, MsgA3001.A3001RES);
		return resMap.get(MsgField.order_no.getFieldCode()).getValue();
	}

	public Map<String, String> queryFundAccts(Map<String, String> map, String[] investAcctNos) throws BizException {
		Map<String, String> resultMap = new HashMap<String, String>();
		map.put(MsgField.h_exch_code.getFieldCode(), "A3002");
		for (int i = 0; i < investAcctNos.length; i++) {
			Map<String, MsgField> record = queryFundAcct(map, investAcctNos[i]);
			String value = record.get(MsgField.curr_can_use.getFieldCode()).getValue();
			resultMap.put(investAcctNos[i], value);
		}
		return resultMap;
	}
	
	public Map<String, Map<String, MsgField>> queryAcctsNeedPay(Map<String, String> map, String[] investAcctNos) throws BizException {
		Map<String, Map<String, MsgField>> resMap = new HashMap<String, Map<String, MsgField>>();
		map.put(MsgField.h_exch_code.getFieldCode(), "A3002");
		for (int i = 0; i < investAcctNos.length; i++) {
			Map<String, MsgField> record = queryFundAcct(map, investAcctNos[i]);
			MsgField debtField = record.get(MsgField.debt_bal.getFieldCode());
			Double debt = Double.parseDouble(debtField.getValue());
			//欠款金额不为零
			if(debt > 0){
				resMap.put(investAcctNos[i], record);
			}
		}
		return resMap;
	}
	
	public Map<String, MsgField> queryOneAcctNeedPay(Map<String, String> map, String investAcctNo) throws BizException {
		Map<String, Map<String, MsgField>> resMap = new HashMap<String, Map<String, MsgField>>();
		map.put(MsgField.h_exch_code.getFieldCode(), "A3002");
		Map<String, MsgField> record = queryFundAcct(map, investAcctNo);
		return record;
	}
	//查询资金账户
	public Map<String, MsgField> queryFundAcct(Map<String, String> map, String investAcctNo) throws BizException{
		map.put(MsgField.h_exch_code.getFieldCode(), "A3002");
		map.put(MsgField.aip_no.getFieldCode(), investAcctNo);
		Map<String, MsgField> resMap = MsgUtil.getResBody(map, MsgA3002.A3002REQ, MsgA3002.A3002RES);
		return resMap;
	}
	//主动定投
	public String doOneDeal(Map<String, String> map) throws BizException {
		map.put(MsgField.h_exch_code.getFieldCode(), "A1014");
		Map<String, MsgField> resMap = MsgUtil.getResBody(map, MsgA1014.A1014REQ, MsgA1014.A1014RES);
		return resMap.get(MsgField.order_no.getFieldCode()).getValue();
	}
	
	//欠费补缴
	public String payDebt(Map<String, String> map) throws BizException {
		map.put(MsgField.h_exch_code.getFieldCode(), "A3001");
		Map<String, MsgField> resMap = MsgUtil.getResBody(map, MsgA3001.A3001REQ, MsgA3001.A3001RES);
		return resMap.get(MsgField.order_no.getFieldCode()).getValue();
	}

}
