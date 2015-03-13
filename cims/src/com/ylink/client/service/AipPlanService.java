package com.ylink.client.service;

import com.ylink.client.domain.AipPlanInfo;
import com.ylink.client.view.AipPlanForm;

public interface AipPlanService {
	/**
	 * 客户定投计划查询
	 */
	public AipPlanInfo findAipPlanService(AipPlanForm aipPlanForm);
}
