package com.ylink.cim.common.state;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractState;
import flink.util.ExceptionUtils;

public class PayState extends AbstractState {
	public static Map<String, PayState> ALL = new LinkedHashMap<String, PayState>();

	public static final PayState UNPAY = new PayState("Î´¸¶¿î", "00");
	public static final PayState PAID = new PayState("ÒÑ¸¶¿î", "01");
	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("payStates", PayState.ALL.values());
	}

	public static PayState valueOf(String value) throws Exception {

		PayState state = ALL.get(value);
		if (null == state) {
			ExceptionUtils.logException(PayState.class, "×´Ì¬´íÎó");
		}
		return state;
	}

	protected PayState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
}
