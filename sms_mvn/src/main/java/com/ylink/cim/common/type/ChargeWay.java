package com.ylink.cim.common.type;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractType;
import flink.util.ExceptionUtils;

public class ChargeWay extends AbstractType {

	/**
	 * 
	 */
	public static Map<String, ChargeWay> ALL = new LinkedHashMap<String, ChargeWay>();

	public static final ChargeWay UNIT = new ChargeWay("�̶�����", "00");
	public static final ChargeWay SEG = new ChargeWay("�ֶμƼ�", "01");
	public static final ChargeWay STEP = new ChargeWay("������", "02");

	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("chargeWays", ChargeWay.ALL.values());
	}

	public static ChargeWay valueOf(String value) throws Exception {

		ChargeWay type = ALL.get(value);
		if (null == type) {
			ExceptionUtils.logException(ChargeWay.class, "���ʹ���");
		}
		return type;
	}

	protected ChargeWay(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
}
