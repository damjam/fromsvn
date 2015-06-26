package com.ylink.cim.common.state;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractType;
import flink.util.ExceptionUtils;

/**
 * 
 * �ʽ�鼯����״̬
 * 
 */
public class FgStepState extends AbstractType {
	public static Map<String, FgStepState> ALL = new LinkedHashMap<String, FgStepState>();

	public static final FgStepState NOT_START = new FgStepState("δ��ʼ", "1");
	public static final FgStepState ON_GOING = new FgStepState("���ڽ���", "2");
	public static final FgStepState SUCCESSFUL = new FgStepState("����ɹ�", "3");
	public static final FgStepState FAILURE = new FgStepState("����ʧ��", "4");

	protected FgStepState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static FgStepState valueOf(String value) throws Exception {

		FgStepState type = ALL.get(value);
		if (null == type) {
			ExceptionUtils.logException(FgStepState.class, "���ʹ���");
		}
		return type;
	}

	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("fgStepStates", FgStepState.ALL.values());
	}
}
