package com.ylink.cim.common.state;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractState;
import flink.util.ExceptionUtils;

/**
 * �ƻ�״̬
 *
 */
public class PlanState extends AbstractState {

	public static Map<String, PlanState> ALL = new LinkedHashMap<String, PlanState>();
	protected PlanState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static final PlanState APPLY = new PlanState("����", "1");
	public static final PlanState EFFECT = new PlanState("��Ч", "2");
	public static final PlanState PAUSE = new PlanState("��ͣ", "3");
	public static final PlanState TERMINAL = new PlanState("��ֹ", "4");
	public static PlanState valueOf(String value) throws Exception {
		PlanState state = ALL.get(value);
		if(null == state){
			ExceptionUtils.logException(PlanState.class, "״̬����");
		}
		return state;
	}
	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("planStates", PlanState.ALL.values());
	}
}
