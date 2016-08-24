package com.ylink.cim.common.state;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractState;
import flink.util.ExceptionUtils;

public class CheckinState extends AbstractState {
	public static Map<String, CheckinState> ALL = new LinkedHashMap<String, CheckinState>();

	public static final CheckinState UNCHECKIN = new CheckinState("未入住", "00");
	public static final CheckinState CHECKEDIN = new CheckinState("已入住", "01");

	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("checkinStates", CheckinState.ALL.values());
	}

	public static CheckinState valueOf(String value) throws Exception {

		CheckinState state = ALL.get(value);
		if (null == state) {
			ExceptionUtils.logException(CheckinState.class, "状态错误");
		}
		return state;
	}

	protected CheckinState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
}
