package com.ylink.cim.common.type;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractType;

public class InputTradeType extends AbstractType {

	public static Map<String, InputTradeType> ALL = new LinkedHashMap<String, InputTradeType>();

	public static final InputTradeType INNER_DEPOSIT = new InputTradeType("�ڲ���ֵ", "�ڲ���ֵ");
	public static final InputTradeType ORDER_INCOME = new InputTradeType("��������", "��������");
	public static final InputTradeType PRESTORE = new InputTradeType("�ͻ�����", "�ͻ�����");
	public static final InputTradeType IN_OTHER = new InputTradeType("��������", "��������");

	protected InputTradeType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static InputTradeType valueOf(String value) {
		InputTradeType type = ALL.get(value);
		return type;
	}

	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("tradeTypes", InputTradeType.ALL.values());
	}
}
