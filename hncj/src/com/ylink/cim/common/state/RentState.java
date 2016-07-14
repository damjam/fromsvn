package com.ylink.cim.common.state;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractState;
import flink.util.ExceptionUtils;

/**
 * 
 * ·¿ÎÝ×âÁÞ×´Ì¬
 *
 */
public class RentState extends AbstractState {
	public static Map<String, RentState> ALL = new LinkedHashMap<String, RentState>();
	//¶Ô¾«×°¹«Ô¢Ö»´æÔÚ
	public static final RentState STATE_00 = new RentState("´ý×â", "00");
	public static final RentState STATE_01 = new RentState("ÒÑ×â", "01");

	protected RentState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static RentState valueOf(String value) throws Exception {

		RentState state = ALL.get(value);
		if (null == state) {
			ExceptionUtils.logException(RentState.class, "×´Ì¬´íÎó");
		}
		return state;
	}

	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("rentStates", RentState.ALL.values());
	}
}
