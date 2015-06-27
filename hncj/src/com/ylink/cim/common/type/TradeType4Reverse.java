package com.ylink.cim.common.type;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractType;

public class TradeType4Reverse extends AbstractType {

	public static Map<String, TradeType4Reverse> ALL = new LinkedHashMap<String, TradeType4Reverse>();
	
	public static final TradeType4Reverse SERVICE = new TradeType4Reverse("��ҵ��(��������Դ��)", "00");
	public static final TradeType4Reverse WATER = new TradeType4Reverse("ˮ��", "01");
	public static final TradeType4Reverse SECURITY = new TradeType4Reverse("��Ѻ��", "03");
	public static final TradeType4Reverse DECORATE = new TradeType4Reverse("װ�޷����", "04");
	public static final TradeType4Reverse PARKING = new TradeType4Reverse("��λά����", "05");
	
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
