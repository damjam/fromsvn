package com.ylink.cim.common.state;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractState;
import flink.util.ExceptionUtils;


public class RecordState extends AbstractState {
	public static Map<String, RecordState> ALL = new LinkedHashMap<String, RecordState>();

	public static final RecordState UNCHECK = new RecordState("Î´ÉóºË", "00");
	public static final RecordState CHECKED= new RecordState("ÒÑÉóºË", "01");
	
	protected RecordState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static RecordState valueOf(String value) throws Exception {

		RecordState state = ALL.get(value);
		if (null == state) {
			ExceptionUtils.logException(RecordState.class, "×´Ì¬´íÎó");
		}
		return state;
	}

	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("billStates", RecordState.ALL.values());
	}
}
