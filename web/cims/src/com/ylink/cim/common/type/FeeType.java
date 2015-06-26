package com.ylink.cim.common.type;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractType;
import flink.util.ExceptionUtils;

public class FeeType extends AbstractType {

	public static Map<String, FeeType> ALL = new LinkedHashMap<String, FeeType>();

	public static final FeeType MANAGE = new FeeType("��ҵ��", "00");
	public static final FeeType LIGHTING = new FeeType("����������", "01");
	public static final FeeType WATER = new FeeType("ˮ��", "02");
	public static final FeeType ELECTIRC = new FeeType("���", "03");
	public static final FeeType CLEAN = new FeeType("װ���������˷�", "04");
	public static final FeeType LIFT = new FeeType("װ�޵���ʹ�÷�", "05");
	public static final FeeType DEPOSIT = new FeeType("װ��Ѻ��", "06");
	public static final FeeType WITHDRAW = new FeeType("��װ��Ѻ��", "07");
	public static final FeeType MAINTAIN = new FeeType("ά�޻���", "08");
	public static final FeeType PARKING = new FeeType("��λ���÷�", "09");
	public static final FeeType OTHER = new FeeType("����", "99");
	protected FeeType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static FeeType valueOf(String value) throws Exception {

		FeeType type = ALL.get(value);
		if (null == type) {
			ExceptionUtils.logException(FeeType.class, "���ʹ���");
		}
		return type;
	}

	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("custTypes", FeeType.ALL.values());
	}
}
