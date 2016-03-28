package com.ylink.cim.common.state;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractState;
import flink.util.ExceptionUtils;

public class ExecuteState extends AbstractState {

	public static Map<String, ExecuteState> ALL = new LinkedHashMap<String, ExecuteState>();
	public static final ExecuteState UN_EXE = new ExecuteState("δ����", "0");
	public static final ExecuteState CALL_SUC = new ExecuteState("���óɹ�", "1");
	public static final ExecuteState CALL_FAIL = new ExecuteState("����ʧ��", "2");
	public static final ExecuteState EXE_SUC = new ExecuteState("����ִ�гɹ�", "3");
	public static final ExecuteState EXE_FAIL = new ExecuteState("����ִ��ʧ��", "4");

	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("executeStates", ExecuteState.ALL.values());
	}

	public static ExecuteState valueOf(String value) throws Exception {
		ExecuteState state = ALL.get(value);
		if (null == state) {
			ExceptionUtils.logException(ExecuteState.class, "״̬����");
		}
		return state;
	}

	protected ExecuteState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
}
