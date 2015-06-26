package com.ylink.cim.common.state;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractState;
import flink.util.ExceptionUtils;

public class OwnerState extends AbstractState {
	public static Map<String, OwnerState> ALL = new LinkedHashMap<String, OwnerState>();
	public static final OwnerState NORMAL = new OwnerState("����", "00");
	public static final OwnerState CANCEL = new OwnerState("������", "01");

	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("billStates", OwnerState.ALL.values());
	}

	public static OwnerState valueOf(String value) throws Exception {

		OwnerState state = ALL.get(value);
		if (null == state) {
			ExceptionUtils.logException(OwnerState.class, "״̬����");
		}
		return state;
	}

	protected OwnerState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
}
