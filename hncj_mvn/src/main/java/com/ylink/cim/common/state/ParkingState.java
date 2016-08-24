package com.ylink.cim.common.state;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractState;
import flink.util.ExceptionUtils;

public class ParkingState extends AbstractState {
	public static Map<String, ParkingState> ALL = new LinkedHashMap<String, ParkingState>();

	public static final ParkingState IDLE = new ParkingState("空置", "00");
	public static final ParkingState SOLD = new ParkingState("已售出", "01");
	public static final ParkingState HIRED = new ParkingState("在租", "02");
	public static final ParkingState OCCUPIED = new ParkingState("内部使用", "03");
	public static final ParkingState RESERVE = new ParkingState("预留", "04");

	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("parkingStates", ParkingState.ALL.values());
	}

	public static ParkingState valueOf(String value) throws Exception {

		ParkingState state = ALL.get(value);
		if (null == state) {
			ExceptionUtils.logException(ParkingState.class, "状态错误");
		}
		return state;
	}

	protected ParkingState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
}
