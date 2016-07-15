package com.ylink.cim.common.type;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractType;

public class InputTradeType extends AbstractType {

	public static Map<String, InputTradeType> ALL = new LinkedHashMap<String, InputTradeType>();

	public static final TradeType INNER_DEPOSIT = new TradeType("�ڲ���ֵ", "�ڲ���ֵ");
	public static final TradeType ORDER_INCOME = new TradeType("��������", "��������");
	public static final TradeType PRESTORE = new TradeType("�ͻ�����", "�ͻ�����");
	public static final TradeType IN_OTHER = new TradeType("��������", "��������");

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
