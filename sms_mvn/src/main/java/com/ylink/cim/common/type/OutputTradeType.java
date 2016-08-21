package com.ylink.cim.common.type;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractType;

public class OutputTradeType extends AbstractType {

	public static Map<String, OutputTradeType> ALL = new LinkedHashMap<String, OutputTradeType>();

	public static final OutputTradeType ELECTRIC = new OutputTradeType("���", "���");
	public static final OutputTradeType WATER_FEE = new OutputTradeType("ˮ��", "ˮ��");
	public static final OutputTradeType CLEAN_FEE = new OutputTradeType("������", "������");
	public static final OutputTradeType HOUSE_RENT = new OutputTradeType("����", "����");
	public static final OutputTradeType SALARY = new OutputTradeType("֧������", "֧������");
	public static final OutputTradeType MATERIAL = new OutputTradeType("����ԭ����", "����ԭ����");
	public static final OutputTradeType REPAIR = new OutputTradeType("�豸ά��ά����", "�豸ά��ά����");
	public static final OutputTradeType DEVICE = new OutputTradeType("�����豸", "�����豸");
	public static final OutputTradeType ADD_GAS = new OutputTradeType("����", "����");
	public static final OutputTradeType LEND_OUT = new OutputTradeType("������", "������");
	public static final OutputTradeType PUBLIC_SPEND = new OutputTradeType("����֧��", "����֧��");
	public static final OutputTradeType INNER_WITHDRAW = new OutputTradeType("��������", "��������");
	public static final OutputTradeType GOODS_REFUND = new OutputTradeType("�˿�", "�˿�");
	public static final OutputTradeType IN_REVERSE = new OutputTradeType("��������", "��������");
	public static final OutputTradeType NORMAL_OUT = new OutputTradeType("�ճ�֧��", "�ճ�֧��");
	public static final OutputTradeType OUT_OTHER = new OutputTradeType("����֧��", "����֧��");
	
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
