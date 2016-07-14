package com.ylink.cim.common.state;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractState;
import flink.util.ExceptionUtils;

public class RenterState extends AbstractState {
	public static Map<String, RenterState> ALL = new LinkedHashMap<String, RenterState>();
	public static final RenterState LIVEIN = new RenterState("正常入住", "00");
	public static final RenterState LIVEOUT = new RenterState("已搬离", "01");

	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("renterStates", RenterState.ALL.values());
	}

	public static RenterState valueOf(String value) throws Exception {

		RenterState state = ALL.get(value);
		if (null == state) {
			ExceptionUtils.logException(RenterState.class, "状态错误");
		}
		return state;
	}

	protected RenterState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
}
