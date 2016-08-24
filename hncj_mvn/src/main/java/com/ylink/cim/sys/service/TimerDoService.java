package com.ylink.cim.sys.service;

import java.util.Date;

import com.ylink.cim.sys.domain.TimerDo;

import flink.etc.BizException;

public interface TimerDoService {

	public abstract boolean insertInitTask(String sTriggerDate)
			throws BizException;

	public abstract void updateTimerDo(TimerDo timerDo, String state,
			String string);

	public abstract void addTimerDo(Class<?> clazz, Date date);

}
