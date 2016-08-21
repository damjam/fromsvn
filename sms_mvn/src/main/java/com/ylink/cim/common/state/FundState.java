package com.ylink.cim.common.state;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractState;
import flink.util.ExceptionUtils;

/**
 * �ʽ�ת״̬
 * 
 * @author
 * 
 */
public class FundState extends AbstractState {

	public static Map<String, FundState> ALL = new LinkedHashMap<String, FundState>();

	protected FundState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static final FundState STATE_1 = new FundState("ָ������", "1");
	public static final FundState STATE_2 = new FundState("ָ���", "2");
	public static final FundState STATE_3 = new FundState("�����ѷ���", "3");
	public static final FundState STATE_4 = new FundState("��������ɹ�", "4");
	public static final FundState STATE_5 = new FundState("��������ʧ��", "5");
	public static final FundState STATE_6 = new FundState("��������ʱ", "6");
	public static final FundState STATE_7 = new FundState("�������ѷ���", "7");
	public static final FundState STATE_8 = new FundState("�����д���ɹ�", "8");
	public static final FundState STATE_9 = new FundState("�����д���ʧ��", "9");
	public static final FundState STATE_10 = new FundState("�ѳ���", "10");

	public static FundState valueOf(String value) throws Exception {
		FundState fundState = ALL.get(value);
		if (null == fundState) {
			ExceptionUtils.logException(FundState.class, "״̬����");
		}
		return fundState;
	}

	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("fundStates", FundState.ALL.values());
	}

}
