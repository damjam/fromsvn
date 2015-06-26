package com.ylink.cim.common.type;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractType;

public class OutputTradeType extends AbstractType {

	public static Map<String, OutputTradeType> ALL = new LinkedHashMap<String, OutputTradeType>();
	
	
	public static final OutputTradeType REFUND = new OutputTradeType("��Ѻ��", "10");
	public static final OutputTradeType ELECTRIC = new OutputTradeType("�����", "11");
	public static final OutputTradeType SALARY = new OutputTradeType("֧������", "12");
	public static final OutputTradeType DEVICE = new OutputTradeType("���������豸", "13");
	public static final OutputTradeType REPAIR = new OutputTradeType("֧��ά�޷�", "14");
	public static final OutputTradeType ADD_GAS = new OutputTradeType("����", "15");
	public static final OutputTradeType WITHDRAW = new OutputTradeType("��ҵ��Ԥ���", "16");
	public static final OutputTradeType INNER_WITHDRAW = new OutputTradeType("�ڲ�����", "17");
	public static final OutputTradeType OUT_OTHER = new OutputTradeType("��������", "19");
	
	
	
	protected OutputTradeType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static OutputTradeType valueOf(String value) {
		OutputTradeType type = ALL.get(value);
		return type;
	}
	
	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("tradeTypes", OutputTradeType.ALL.values());
	}
}
