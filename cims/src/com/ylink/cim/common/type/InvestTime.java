package com.ylink.cim.common.type;

import java.util.LinkedHashMap;
import java.util.Map;

import flink.util.AbstractType;
import flink.util.ExceptionUtils;

public class InvestTime extends AbstractType {

	public static Map<String, InvestTime> ALL = new LinkedHashMap<String, InvestTime>();

	public static final InvestTime TYPE_1 = new InvestTime("��ֹ����", "1");
	public static final InvestTime TYPE_2 = new InvestTime("�ۿ�����", "2");
	public static final InvestTime TYPE_3 = new InvestTime("�ۼƿۿ���", "3");
	public static final InvestTime TYPE_4 = new InvestTime("�ۼƶ�Ͷ����", "4");

	protected InvestTime(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static InvestTime valueOf(String value) throws Exception {

		InvestTime type = ALL.get(value);
		if (null == type) {
			ExceptionUtils.logException(InvestTime.class, "���ʹ���");
		}
		return type;
	}
}
