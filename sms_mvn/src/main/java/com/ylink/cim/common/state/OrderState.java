package com.ylink.cim.common.state;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractState;
import flink.util.ExceptionUtils;

public class OrderState extends AbstractState {
	public static Map<String, OrderState> ALL = new LinkedHashMap<String, OrderState>();

	public static final OrderState INIT = new OrderState("初始", "00");
	public static final OrderState DEALING = new OrderState("处理中", "01");
	public static final OrderState FINISHED = new OrderState("已完成", "02");
	public static final OrderState CANCELED = new OrderState("已取消", "09");
	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("orderStates", OrderState.ALL.values());
	}

	public static OrderState valueOf(String value) throws Exception {

		OrderState state = ALL.get(value);
		if (null == state) {
			ExceptionUtils.logException(OrderState.class, "状态错误");
		}
		return state;
	}

	protected OrderState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
}
