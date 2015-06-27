package com.ylink.cim.common.state;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractState;
import flink.util.ExceptionUtils;

public class BillTrackState extends AbstractState {
	public static Map<String, BillTrackState> ALL = new LinkedHashMap<String, BillTrackState>();

	public static final BillTrackState VALID = new BillTrackState("��Ч", "00");
	public static final BillTrackState EXPIRED = new BillTrackState("ʧЧ", "01");

	protected BillTrackState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static BillTrackState valueOf(String value) throws Exception {

		BillTrackState state = ALL.get(value);
		if (null == state) {
			ExceptionUtils.logException(BillTrackState.class, "״̬����");
		}
		return state;
	}

	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("billStates", BillTrackState.ALL.values());
	}
}
