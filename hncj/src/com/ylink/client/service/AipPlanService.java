package com.ylink.client.service;

import com.ylink.client.domain.AipPlanInfo;
import com.ylink.client.view.AipPlanForm;

public interface AipPlanService {
	/**
	 * �ͻ���Ͷ�ƻ���ѯ
	 */
	public AipPlanInfo findAipPlanService(AipPlanForm aipPlanForm);
}
