package com.ylink.cim.common.type;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractType;

public class TradeType4Reverse extends AbstractType {

	public static Map<String, TradeType4Reverse> ALL = new LinkedHashMap<String, TradeType4Reverse>();

	public static final TradeType4Reverse ORDER_INCOME = new TradeType4Reverse("订单收入", "订单收入");
	
	protected TradeType4Reverse(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static TradeType4Reverse valueOf(String value) {
		TradeType4Reverse type = ALL.get(value);
		return type;
	}

	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("tradeTypes", TradeType4Reverse.ALL.values());
	}
}
