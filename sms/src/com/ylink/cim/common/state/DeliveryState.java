package com.ylink.cim.common.state;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractState;
import flink.util.ExceptionUtils;

public class DeliveryState extends AbstractState {
	public static Map<String, DeliveryState> ALL = new LinkedHashMap<String, DeliveryState>();
	
	public static final DeliveryState INIT = new DeliveryState("������", "00");
	public static final DeliveryState SENT = new DeliveryState("�ѷ���", "01");
	public static final DeliveryState RECEIVED = new DeliveryState("���ջ�", "02");
	public static final DeliveryState RETURNED = new DeliveryState("���˻�", "03");
	public static final DeliveryState CANCELED = new DeliveryState("��ȡ��", "09");
	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("deliveryStates", DeliveryState.ALL.values());
	}

	public static DeliveryState valueOf(String value) throws Exception {

		DeliveryState state = ALL.get(value);
		if (null == state) {
			ExceptionUtils.logException(DeliveryState.class, "״̬����");
		}
		return state;
	}

	protected DeliveryState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
}
