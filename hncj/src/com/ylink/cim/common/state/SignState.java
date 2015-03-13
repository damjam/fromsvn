package com.ylink.cim.common.state;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractState;
import flink.util.ExceptionUtils;

public class SignState extends AbstractState {

	public static Map<String, SignState> ALL = new LinkedHashMap<String, SignState>();
	protected SignState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	public static final SignState UNCONFIRM_ADD = new SignState("������ȷ��", "0");
	public static final SignState SIGNED = new SignState("����", "1");
	public static final SignState CANCELED_ACCT = new SignState("������", "2");
	public static final SignState UNCONFIRM_UPDATE = new SignState("���Ĵ�ȷ��", "3");
	public static final SignState CANCELING_ACCT = new SignState("������Լ��", "4");
	public static final SignState CANCELING_SIGN = new SignState("�����˻��󶨽�Լ��", "5");
	public static final SignState UNSIGN = new SignState("δǩԼ", "6");//��Լ��
	//�޹���ǩԼ��ʱ
	public static SignState valueOf(String value) throws Exception {
		SignState state = ALL.get(value);
		if(null == state){
			ExceptionUtils.logException(SignState.class, "״̬����");
		}
		return state;
	}
	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("signStates", SignState.ALL.values());
	}
}
