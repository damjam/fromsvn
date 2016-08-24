package com.ylink.cim.common.state;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractType;
import flink.util.ExceptionUtils;

/**
 * 
 * 装修状态
 *
 */
public class DecorateState extends AbstractType {
	public static Map<String, DecorateState> ALL = new LinkedHashMap<String, DecorateState>();
	public static final DecorateState STATE_00 = new DecorateState("未装修", "00");
	public static final DecorateState STATE_01 = new DecorateState("装修中", "01");
	public static final DecorateState STATE_02 = new DecorateState("已装修", "02");

	protected DecorateState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static DecorateState valueOf(String value) throws Exception {

		DecorateState state = ALL.get(value);
		if (null == state) {
			ExceptionUtils.logException(DecorateState.class, "状态错误");
		}
		return state;
	}

	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("decorateStates", DecorateState.ALL.values());
	}
}
