package com.ylink.cim.common.state;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractState;
import flink.util.ExceptionUtils;


public class BillState extends AbstractState {
	public static Map<String, BillState> ALL = new LinkedHashMap<String, BillState>();

	public static final BillState UNPAY = new BillState("����", "00");
	public static final BillState PAID= new BillState("�ѽ�", "01");
	public static final BillState REFUND = new BillState("���˿�", "02");
	public static final BillState PART_PAID = new BillState("�����ѽ�", "03");
	public static final BillState REVERSE = new BillState("�ѳ���", "04");
	protected BillState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static BillState valueOf(String value) throws Exception {

		BillState state = ALL.get(value);
		if (null == state) {
			ExceptionUtils.logException(BillState.class, "״̬����");
		}
		return state;
	}

	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("billStates", BillState.ALL.values());
	}
}
