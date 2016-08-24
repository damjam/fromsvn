package com.ylink.cim.common.state;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractState;
import flink.util.ExceptionUtils;

/**
 * 
 * 房屋租赁状态
 *
 */
public class RentState extends AbstractState {
	public static Map<String, RentState> ALL = new LinkedHashMap<String, RentState>();
	//对精装公寓只存在
	public static final RentState STATE_00 = new RentState("待租", "00");
	public static final RentState STATE_01 = new RentState("已租", "01");

	protected RentState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static RentState valueOf(String value) throws Exception {

		RentState state = ALL.get(value);
		if (null == state) {
			ExceptionUtils.logException(RentState.class, "状态错误");
		}
		return state;
	}

	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("rentStates", RentState.ALL.values());
	}
}
