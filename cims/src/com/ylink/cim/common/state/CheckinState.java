package com.ylink.cim.common.state;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractType;
import flink.util.ExceptionUtils;

public class CheckinState extends AbstractType {
	public static Map<String, CheckinState> ALL = new LinkedHashMap<String, CheckinState>();

	public static final CheckinState UNCHECKIN = new CheckinState("δ��ס", "00");
	public static final CheckinState CHECKEDIN = new CheckinState("����ס", "01");

	protected CheckinState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static CheckinState valueOf(String value) throws Exception {

		CheckinState state = ALL.get(value);
		if (null == state) {
			ExceptionUtils.logException(CheckinState.class, "״̬����");
		}
		return state;
	}

	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("checkinStates", CheckinState.ALL.values());
	}
}
