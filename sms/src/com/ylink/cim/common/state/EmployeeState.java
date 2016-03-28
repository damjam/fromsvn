package com.ylink.cim.common.state;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractState;
import flink.util.ExceptionUtils;

public class EmployeeState extends AbstractState {
	public static Map<String, EmployeeState> ALL = new LinkedHashMap<String, EmployeeState>();

	public static final EmployeeState NORMAL = new EmployeeState("ÔÚÖ°", "00");
	public static final EmployeeState LEAVE = new EmployeeState("ÐÝ¼Ù", "01");
	public static final EmployeeState QUIT = new EmployeeState("ÀëÖ°", "02");//
	
	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("employeeStates", EmployeeState.ALL.values());
	}

	public static EmployeeState valueOf(String value) throws Exception {

		EmployeeState state = ALL.get(value);
		if (null == state) {
			ExceptionUtils.logException(EmployeeState.class, "×´Ì¬´íÎó");
		}
		return state;
	}

	protected EmployeeState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
}
