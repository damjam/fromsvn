package com.ylink.cim.common.type;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractType;
import flink.util.ExceptionUtils;

/**
 * 
 * �ƻ�����ԭ��
 * 
 */
public class PlanApplyReason extends AbstractType {
	public static Map<String, PlanApplyReason> ALL = new LinkedHashMap<String, PlanApplyReason>();

	public static final PlanApplyReason APPLY_REASON_1 = new PlanApplyReason("������", "1");
	public static final PlanApplyReason APPLY_REASON_2 = new PlanApplyReason("�����޸�->����", "2");
	public static final PlanApplyReason APPLY_REASON_3 = new PlanApplyReason("��Ч�޸�->����", "3");
	public static final PlanApplyReason APPLY_REASON_4 = new PlanApplyReason("��Ч��ͣ->���루��ͣ��", "4");
	public static final PlanApplyReason APPLY_REASON_5 = new PlanApplyReason("��ͣ�ָ�->����", "5");

	protected PlanApplyReason(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static PlanApplyReason valueOf(String value) throws Exception {

		PlanApplyReason type = ALL.get(value);
		if (null == type) {
			ExceptionUtils.logException(PlanApplyReason.class, "���ʹ���");
		}
		return type;
	}

	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("planApplyReasons", PlanApplyReason.ALL.values());
	}
}
