package com.ylink.cim.common.state;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractState;
import flink.util.ExceptionUtils;

/**
 * 
 * ί��״̬
 * 
 */
public class AipEntrState extends AbstractState {
	public static Map<String, AipEntrState> ALL = new LinkedHashMap<String, AipEntrState>();

	public static final AipEntrState APPLY = new AipEntrState("����", "1");
	public static final AipEntrState ALREADY_IN_FORCE = new AipEntrState("����Ч",
			"2");
	public static final AipEntrState HAVE_BEEN_PROCESSED = new AipEntrState(
			"�Ѵ���", "3");
	public static final AipEntrState HAVE_BEEN_REVOKED = new AipEntrState(
			"�ѳ���", "4");

	protected AipEntrState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static AipEntrState valueOf(String value) throws Exception {

		AipEntrState state = ALL.get(value);
		if (null == state) {
			ExceptionUtils.logException(AipEntrState.class, "״̬����");
		}
		return state;
	}

	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("aipEntrStates", AipEntrState.ALL.values());
	}
}
