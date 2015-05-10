package com.ylink.cim.common.state;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractState;
import flink.util.ExceptionUtils;

public class ExecuteState extends AbstractState {

	public static Map<String, ExecuteState> ALL = new LinkedHashMap<String, ExecuteState>();
	public static final ExecuteState UN_EXE = new ExecuteState("未运行", "0");
	public static final ExecuteState CALL_SUC = new ExecuteState("调用成功", "1");
	public static final ExecuteState CALL_FAIL = new ExecuteState("调用失败", "2");
	public static final ExecuteState EXE_SUC = new ExecuteState("命令执行成功", "3");
	public static final ExecuteState EXE_FAIL = new ExecuteState("命令执行失败", "4");
	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("executeStates", ExecuteState.ALL.values());
	}
	public static ExecuteState valueOf(String value) throws Exception {
		ExecuteState state = ALL.get(value);
		if(null == state){
			ExceptionUtils.logException(ExecuteState.class, "状态错误");
		}
		return state;
	}
	protected ExecuteState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
}
