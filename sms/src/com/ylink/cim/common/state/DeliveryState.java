package com.ylink.cim.common.state;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractState;
import flink.util.ExceptionUtils;

public class DeliveryState extends AbstractState {
	public static Map<String, DeliveryState> ALL = new LinkedHashMap<String, DeliveryState>();
	
	public static final DeliveryState INIT = new DeliveryState("备货中", "00");
	public static final DeliveryState SENT = new DeliveryState("已发货", "01");
	public static final DeliveryState RECEIVED = new DeliveryState("已收货", "02");
	public static final DeliveryState RETURNED = new DeliveryState("已退货", "03");
	public static final DeliveryState CANCELED = new DeliveryState("已取消", "09");
	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("deliveryStates", DeliveryState.ALL.values());
	}

	public static DeliveryState valueOf(String value) throws Exception {

		DeliveryState state = ALL.get(value);
		if (null == state) {
			ExceptionUtils.logException(DeliveryState.class, "状态错误");
		}
		return state;
	}

	protected DeliveryState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
}
