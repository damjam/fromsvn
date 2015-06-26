package com.ylink.cim.common.state;

/**
 * ���״̬
 * 
 */
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractState;
import flink.util.ExceptionUtils;

public class TakeState extends AbstractState {

	public static Map<String, TakeState> ALL = new LinkedHashMap<String, TakeState>();

	protected TakeState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static final TakeState STATE_1 = new TakeState("�������", "1");
	public static final TakeState STATE_2 = new TakeState("�ͻ�����", "2");
	public static final TakeState STATE_3 = new TakeState("�ۿ�ɹ�", "3");
	public static final TakeState STATE_4 = new TakeState("�ۿ�ʧ��", "4");
	public static final TakeState STATE_5 = new TakeState("���ȷ��", "5");
	public static final TakeState STATE_6 = new TakeState("���е��ճ���", "6");
	public static final TakeState STATE_7 = new TakeState("���д��ճ���", "7");
	public static final TakeState STATE_8 = new TakeState("ϵͳ����", "8");
	public static final TakeState STATE_9 = new TakeState("�����", "9");
	public static final TakeState STATE_10 = new TakeState("���ʧ��", "10");
	public static final TakeState STATE_11 = new TakeState("��Ͷ�Զ�����", "11");

	public static TakeState valueOf(String value) throws Exception {
		TakeState state = ALL.get(value);
		if (null == state) {
			ExceptionUtils.logException(TakeState.class, "״̬����");
		}
		return state;
	}

	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("takeStates", TakeState.ALL.values());
	}
}
