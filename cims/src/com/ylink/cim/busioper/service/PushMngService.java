package com.ylink.cim.busioper.service;

import com.ylink.cim.busioper.domain.PushPlan;

import flink.etc.BizException;

public interface PushMngService {

	void exeAddRecordTask(String timerDoId) throws BizException;
	void exeSendMsgTask(String timerDoId) throws BizException; 
	void addPushPlan(PushPlan pushPlan);
}
