package com.ylink.cim.common.type;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractType;

public class OutputTradeType extends AbstractType {

	public static Map<String, OutputTradeType> ALL = new LinkedHashMap<String, OutputTradeType>();

	public static final TradeType ELECTRIC = new TradeType("���", "���");
	public static final TradeType WATER_FEE = new TradeType("ˮ��", "ˮ��");
	public static final TradeType CLEAN_FEE = new TradeType("������", "������");
	public static final TradeType HOUSE_RENT = new TradeType("����", "����");
	public static final TradeType SALARY = new TradeType("֧������", "֧������");
	public static final TradeType MATERIAL = new TradeType("����ԭ����", "����ԭ����");
	public static final TradeType REPAIR = new TradeType("�豸ά��ά����", "�豸ά��ά����");
	public static final TradeType DEVICE = new TradeType("�����豸", "�����豸");
	public static final TradeType ADD_GAS = new TradeType("����", "����");
	public static final TradeType LEND_OUT = new TradeType("������", "������");
	public static final TradeType PUBLIC_SPEND = new TradeType("����֧��", "����֧��");
	public static final TradeType INNER_WITHDRAW = new TradeType("��������", "��������");
	public static final TradeType GOODS_REFUND = new TradeType("�˿�", "�˿�");
	public static final TradeType IN_REVERSE = new TradeType("��������", "��������");
	public static final TradeType NORMAL_OUT = new TradeType("�ճ�֧��", "�ճ�֧��");
	public static final TradeType OUT_OTHER = new TradeType("����֧��", "����֧��");
	
	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("tradeTypes", OutputTradeType.ALL.values());
	}

	public static OutputTradeType valueOf(String value) {
		OutputTradeType type = ALL.get(value);
		return type;
	}

	protected OutputTradeType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
}
