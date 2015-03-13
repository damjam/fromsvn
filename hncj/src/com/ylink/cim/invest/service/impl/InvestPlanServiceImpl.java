package com.ylink.cim.invest.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.ylink.cim.common.msg.handle.MsgA1011;
import com.ylink.cim.common.msg.handle.MsgA1012;
import com.ylink.cim.common.msg.handle.MsgA1013;
import com.ylink.cim.common.msg.handle.MsgA1015;
import com.ylink.cim.common.msg.handle.MsgA2032;
import com.ylink.cim.common.msg.handle.MsgField;
import com.ylink.cim.common.msg.handle.MsgUtil;
import com.ylink.cim.common.util.SocketUtil;
import com.ylink.cim.invest.service.InvestPlanService;

import flink.etc.BizException;
import flink.util.Pager;
import flink.util.Paginater;
@Component("investPlanService")
public class InvestPlanServiceImpl implements InvestPlanService {

	public Paginater queryPlan(Map<String, String> map, Pager pager) throws BizException{
		map.put(MsgField.h_exch_code.getFieldCode(), "A2032");
		return MsgUtil.getResBodyPager(map, MsgA2032.A2032REQ, MsgA2032.A2032RES, pager);
	}

	public Map<String, MsgField>  queryOnlyPlan(Map<String, String> map) throws BizException{
			map.put(MsgField.h_exch_code.getFieldCode(), "A2032");
			List<Map<String, MsgField>> list = new ArrayList<Map<String, MsgField>>();
			String msg=MsgUtil.buildReqMsg(map, MsgA2032.A2032REQ);
			String rspMsg=SocketUtil.sendRec(msg);
			list=MsgUtil.parseResBodyList(rspMsg, MsgA2032.A2032RES);
			if(list!=null && list.size()>0){
				return list.get(0);
			}else{
				return null;
			}
	}
	
	
	public String addPlan(Map<String, String> map) throws BizException {
		map.put(MsgField.h_exch_code.getFieldCode(), "A1011");
		String msg=MsgUtil.buildReqMsg(map, MsgA1011.A1011REQ);
		String rspMsg=SocketUtil.sendRec(msg);
		Map<String, MsgField> bodyMap = MsgUtil.parseResBody(rspMsg, MsgA1011.A1011RES);
		String orderNo=bodyMap.get(MsgField.order_no.getFieldCode()).getValue();
		return orderNo;
	}

	
	
	public String updatePlan(Map<String, String> map) throws BizException {
		map.put(MsgField.h_exch_code.getFieldCode(), "A1012");
		String msg=MsgUtil.buildReqMsg(map, MsgA1012.A1012REQ);
		String rspMsg=SocketUtil.sendRec(msg);
		Map<String, MsgField> bodyMap = MsgUtil.parseResBody(rspMsg, MsgA1012.A1012RES);
		
		return bodyMap.get(MsgField.order_no.getFieldCode()).getValue();
	}
	

	public String doPauseOrRecover(Map<String, String> map) throws BizException {
		map.put(MsgField.h_exch_code.getFieldCode(), "A1015");
		String msg=MsgUtil.buildReqMsg(map, MsgA1015.A1015REQ);
		String rspMsg=SocketUtil.sendRec(msg);
		Map<String, MsgField> bodyMap = MsgUtil.parseResBody(rspMsg, MsgA1015.A1015RES);
		
		return bodyMap.get(MsgField.order_no.getFieldCode()).getValue();
	}

	
	
	public String doCancel(Map<String, String> map) throws BizException {
		map.put(MsgField.h_exch_code.getFieldCode(), "A1013");
		String msg=MsgUtil.buildReqMsg(map, MsgA1013.A1013REQ);
		String rspMsg=SocketUtil.sendRec(msg);
		Map<String, MsgField> bodyMap = MsgUtil.parseResBody(rspMsg, MsgA1013.A1013RES);
		
		return bodyMap.get(MsgField.order_no.getFieldCode()).getValue();
	}

	public String addTradeGold(Map<String, Object> map) throws BizException {
		return "A0020";
	}


	
}
