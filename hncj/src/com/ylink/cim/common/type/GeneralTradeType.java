package com.ylink.cim.common.type;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractType;

public class GeneralTradeType extends AbstractType {

	public static Map<String, GeneralTradeType> ALL = new LinkedHashMap<String, GeneralTradeType>();
	
	public static final TradeType CARD_FEE = new TradeType("���ܿ������", "07");
	public static final TradeType PASS_FEE = new TradeType("��ʱ����֤�����", "08");
	public static final TradeType AD_RENT = new TradeType("���λ���޷�", "20");
	public static final TradeType IN_OTHER = new TradeType("��������", "98");
	protected GeneralTradeType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static GeneralTradeType valueOf(String value) {
		GeneralTradeType type = ALL.get(value);
		return type;
	}
	
	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("generalTradeTypes", GeneralTradeType.ALL.values());
	}
}
