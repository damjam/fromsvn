package com.ylink.cim.busioper.service;

import com.ylink.cim.busioper.domain.PushPlan;

import flink.etc.BizException;

public interface PushMngService {

	void exeAddRecordTask(Long timerDoId) throws BizException;
	void exeSendMsgTask(Long timerDoId) throws BizException; 
	void addPushPlan(PushPlan pushPlan);
}
