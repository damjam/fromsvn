package com.ylink.cim.common.type;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractType;
import flink.util.ExceptionUtils;

/**
 * 
 * ��������
 * 
 */
public class AipFareKind extends AbstractType {
	public static Map<String, AipFareKind> ALL = new LinkedHashMap<String, AipFareKind>();

	public static final AipFareKind TRANSACTION_COST = new AipFareKind("���׷���", "1");
	public static final AipFareKind CURRENT_INTEREST_RATES = new AipFareKind("��������", "2");
	public static final AipFareKind WAREHOUSING_COSTS = new AipFareKind("�ִ�����", "3");
	public static final AipFareKind ACCOUNT_MANAGEMENT_FEES = new AipFareKind("�˻������", "4");

	protected AipFareKind(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static AipFareKind valueOf(String value) throws Exception {

		AipFareKind type = ALL.get(value);
		if (null == type) {
			ExceptionUtils.logException(AipFareKind.class, "���ʹ���");
		}
		return type;
	}

	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("aipFareKinds", AipFareKind.ALL.values());
	}
}
