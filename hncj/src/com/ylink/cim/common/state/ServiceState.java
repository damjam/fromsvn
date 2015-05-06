package com.ylink.cim.common.state;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractState;
import flink.util.ExceptionUtils;

public class ServiceState extends AbstractState {

	public static Map<String, ServiceState> ALL = new LinkedHashMap<String, ServiceState>();

	protected ServiceState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static final ServiceState NORMAL = new ServiceState("�ѿ�ͨ", "0");
	public static final ServiceState CANCELED = new ServiceState("��ȡ��", "1");

	public static ServiceState valueOf(String value) throws Exception {
		ServiceState state = ALL.get(value);
		if (null == state) {
			ExceptionUtils.logException(ServiceState.class, "״̬����");
		}
		return state;
	}

	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("serviceStates", ServiceState.ALL.values());
	}
}
