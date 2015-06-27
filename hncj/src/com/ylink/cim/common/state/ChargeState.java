package com.ylink.cim.common.state;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractState;
import flink.util.ExceptionUtils;

/**
 * �ۿ�״̬
 * 
 * @author
 *
 */
public class ChargeState extends AbstractState {

	public static Map<String, ChargeState> ALL = new LinkedHashMap<String, ChargeState>();

	protected ChargeState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static final ChargeState NOT = new ChargeState("δ�ۿ�", "1");
	public static final ChargeState NORMAL = new ChargeState("�����ۿ�", "2");
	public static final ChargeState PART = new ChargeState("���ֿۿ�", "3");
	public static final ChargeState FAIL = new ChargeState("�ۿ�ʧ��", "4");

	public static ChargeState valueOf(String value) throws Exception {
		ChargeState state = ALL.get(value);
		if (null == state) {
			ExceptionUtils.logException(ChargeState.class, "״̬����");
		}
		return state;
	}

	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("chargeStates", ChargeState.ALL.values());
	}
}
