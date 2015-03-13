package com.ylink.cim.common.state;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractState;
import flink.util.ExceptionUtils;

/**
 * 
 * ����״̬
 *
 */
public class RequState extends AbstractState {

	public static Map<String, RequState> ALL = new LinkedHashMap<String, RequState>();
	protected RequState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static final RequState NEW = new RequState("������", "1");
	public static final RequState MODIFY = new RequState("�޸�����", "2");
	public static final RequState REVOKED = new RequState("���볷��", "3");
	public static RequState valueOf(String value) throws Exception {
		RequState state = ALL.get(value);
		if(null == state){
			ExceptionUtils.logException(RequState.class, "״̬����");
		}
		return state;
	}
	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("applyStates", RequState.ALL.values());
	}
}
