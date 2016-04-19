package com.ylink.cim.common.state;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractState;
import flink.util.ExceptionUtils;

public class EmployeeState extends AbstractState {
	public static Map<String, EmployeeState> ALL = new LinkedHashMap<String, EmployeeState>();

	public static final EmployeeState NORMAL = new EmployeeState("在职", "在职");
	public static final EmployeeState LEAVE = new EmployeeState("休假", "在职");
	public static final EmployeeState QUIT = new EmployeeState("离职", "离职");//
	
	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("employeeStates", EmployeeState.ALL.values());
	}

	public static EmployeeState valueOf(String value) throws Exception {

		EmployeeState state = ALL.get(value);
		if (null == state) {
			ExceptionUtils.logException(EmployeeState.class, "状态错误");
		}
		return state;
	}

	protected EmployeeState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
}
