package com.ylink.cim.invest.service;

import java.util.Map;

import com.ylink.cim.common.msg.handle.MsgField;

import flink.etc.BizException;
import flink.util.Pager;
import flink.util.Paginater;

public interface InvestPlanService {
	
	public Paginater queryPlan(Map<String, String> map, Pager pager) throws BizException;

	public String addPlan(Map<String, String> map) throws BizException;
	public String updatePlan(Map<String, String> map) throws BizException;
	public String doPauseOrRecover(Map<String, String> map) throws BizException;
	public String doCancel(Map<String, String> map) throws BizException;
	public Map<String, MsgField> queryOnlyPlan(Map<String, String> map) throws BizException;
	public String addTradeGold(Map<String, Object> map) throws BizException;
}
