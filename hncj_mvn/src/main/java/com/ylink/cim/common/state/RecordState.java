package com.ylink.cim.common.state;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractState;
import flink.util.ExceptionUtils;

public class RecordState extends AbstractState {
	public static Map<String, RecordState> ALL = new LinkedHashMap<String, RecordState>();

	public static final RecordState UNCHECK = new RecordState("未审核", "00");
	public static final RecordState CHECKED = new RecordState("已审核", "01");

	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("billStates", RecordState.ALL.values());
	}

	public static RecordState valueOf(String value) throws Exception {

		RecordState state = ALL.get(value);
		if (null == state) {
			ExceptionUtils.logException(RecordState.class, "状态错误");
		}
		return state;
	}

	protected RecordState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
}
