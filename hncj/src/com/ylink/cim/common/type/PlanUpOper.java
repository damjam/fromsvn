package com.ylink.cim.common.type;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractType;
import flink.util.ExceptionUtils;

/**
 * 
 * �ƻ����²���
 * 
 */
public class PlanUpOper extends AbstractType {
	public static Map<String, PlanUpOper> ALL = new LinkedHashMap<String, PlanUpOper>();

	public static final PlanUpOper APPLY_TO_UNDO = new PlanUpOper("����->����", "1");
	public static final PlanUpOper APPLY_TO_MODIFY = new PlanUpOper("����->�޸�",
			"2");
	public static final PlanUpOper EFFECT_TO_MODIFY = new PlanUpOper("��Ч->�޸�",
			"3");
	public static final PlanUpOper EFFECT_TO_TERMINAL = new PlanUpOper(
			"��Ч->��ֹ", "4");
	public static final PlanUpOper EFFECT_TO_PAUSE = new PlanUpOper("��Ч->��ͣ",
			"5");
	public static final PlanUpOper PAUSE_TO_RESTORE = new PlanUpOper("��ͣ->�ָ�",
			"6");
	public static final PlanUpOper PAUSE_TO_TERMINAL = new PlanUpOper("��ͣ->��ֹ",
			"7");
	public static final PlanUpOper MORETHAN_FAILUREPHASES_TO_TERMINAL = new PlanUpOper(
			"�����ۿ�ʧ������->��ֹ", "8");

	protected PlanUpOper(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static PlanUpOper valueOf(String value) throws Exception {

		PlanUpOper type = ALL.get(value);
		if (null == type) {
			ExceptionUtils.logException(PlanUpOper.class, "���ʹ���");
		}
		return type;
	}

	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("planUpOpers", PlanUpOper.ALL.values());
	}
}
