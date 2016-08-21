package com.ylink.cim.manage.service;

import flink.etc.BizException;

public interface DailyOrderService {

	void exeDailySumTask(String id) throws BizException;
	
	void dailySum() throws BizException;
}
