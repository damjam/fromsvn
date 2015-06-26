package com.ylink.cim.common.type;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractType;

public class TradeType extends AbstractType {

	public static Map<String, TradeType> ALL = new LinkedHashMap<String, TradeType>();
	
	public static final TradeType SERVICE = new TradeType("��ҵ��(��������Դ��)", "00");
	public static final TradeType WATER = new TradeType("ˮ��", "01");
	public static final TradeType DEPOSIT = new TradeType("ҵ����ֵ", "02");
	public static final TradeType SECURITY = new TradeType("��Ѻ��", "03");
	public static final TradeType DECORATE = new TradeType("װ�޷����", "04");
	public static final TradeType PARKING = new TradeType("��λά����", "05");
	public static final TradeType INNER_DEPOSIT = new TradeType("�ڲ���ֵ", "06");
	public static final TradeType IN_OTHER = new TradeType("��������", "09");
	
	public static final TradeType REFUND = new TradeType("��Ѻ��", "10");
	public static final TradeType ELECTRIC = new TradeType("�����", "11");
	public static final TradeType SALARY = new TradeType("֧������", "12");
	public static final TradeType DEVICE = new TradeType("���������豸", "13");
	public static final TradeType REPAIR = new TradeType("֧��ά�޷�", "14");
	public static final TradeType ADD_GAS = new TradeType("����", "15");
	public static final TradeType WITHDRAW = new TradeType("��ҵ��Ԥ���", "16");
	public static final TradeType INNER_WITHDRAW = new TradeType("��������", "17");
	public static final TradeType IN_REVERSE = new TradeType("��������", "18");
	public static final TradeType OUT_OTHER = new TradeType("����֧��", "19");
	
	protected TradeType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static TradeType valueOf(String value) {
		TradeType type = ALL.get(value);
		return type;
	}
	
	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("tradeTypes", TradeType.ALL.values());
	}
}
