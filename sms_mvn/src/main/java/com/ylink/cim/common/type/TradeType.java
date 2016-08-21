package com.ylink.cim.common.type;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractType;

public class TradeType extends AbstractType {

	public static Map<String, TradeType> ALL = new LinkedHashMap<String, TradeType>();

	//public static final TradeType SERVICE = new TradeType("��ҵ��(��������Դ��)", "00");
	//public static final TradeType WATER = new TradeType("ˮ��", "01");
	//public static final TradeType DEPOSIT = new TradeType("ҵ����ֵ", "02");
	//public static final TradeType SECURITY = new TradeType("��Ѻ��", "03");
	//public static final TradeType DECORATE = new TradeType("װ�޷����", "04");
	//public static final TradeType PARKING = new TradeType("��λά����", "05");
	//public static final TradeType CARD_FEE = new TradeType("���ܿ������", "07");
	//public static final TradeType PASS_FEE = new TradeType("��ʱ����֤�����", "08");
	//public static final TradeType AD_RENT = new TradeType("���λ���޷�", "20");
	//public static final TradeType IC_DEPOSIT = new TradeType("IC����ֵ", "21");
	public static final TradeType INNER_DEPOSIT = new TradeType("�ڲ���ֵ", "�ڲ���ֵ");
	public static final TradeType ORDER_INCOME = new TradeType("��������", "��������");
	public static final TradeType PRESTORE = new TradeType("�ͻ�����", "�ͻ�����");
	public static final TradeType IN_OTHER = new TradeType("��������", "��������");

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
