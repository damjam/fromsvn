package com.ylink.cim.common.type;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractType;
import flink.util.ExceptionUtils;

public class NumberTrans extends AbstractType {
	public static Map<String, NumberTrans> ALL = new LinkedHashMap<String, NumberTrans>();

	public static final NumberTrans NUM_1 = new NumberTrans("һ", "1");
	public static final NumberTrans NUM_2 = new NumberTrans("��", "2");
	public static final NumberTrans NUM_3 = new NumberTrans("��", "3");
	public static final NumberTrans NUM_4 = new NumberTrans("��", "4");
	public static final NumberTrans NUM_5 = new NumberTrans("��", "5");
	public static final NumberTrans NUM_6 = new NumberTrans("��", "6");
	public static final NumberTrans NUM_7 = new NumberTrans("��", "7");
	public static final NumberTrans NUM_8 = new NumberTrans("��", "8");
	public static final NumberTrans NUM_9 = new NumberTrans("��", "9");
	public static final NumberTrans NUM_10 = new NumberTrans("ʮ", "10");
	public static final NumberTrans NUM_11 = new NumberTrans("ʮһ", "11");
	public static final NumberTrans NUM_12 = new NumberTrans("ʮ��", "12");
	public static final NumberTrans NUM_13 = new NumberTrans("ʮ��", "13");
	public static final NumberTrans NUM_14 = new NumberTrans("ʮ��", "14");
	public static final NumberTrans NUM_15 = new NumberTrans("ʮ��", "15");
	public static final NumberTrans NUM_16 = new NumberTrans("ʮ��", "16");
	public static final NumberTrans NUM_17 = new NumberTrans("ʮ��", "17");
	public static final NumberTrans NUM_18 = new NumberTrans("ʮ��", "18");
	public static final NumberTrans NUM_19 = new NumberTrans("ʮ��", "19");
	public static final NumberTrans NUM_20 = new NumberTrans("��ʮ", "20");

	protected NumberTrans(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static NumberTrans valueOf(String value) throws Exception {

		NumberTrans type = ALL.get(value);
		if (null == type) {
			ExceptionUtils.logException(NumberTrans.class, "���ʹ���");
		}
		return type;
	}

	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("aipChanels", NumberTrans.ALL.values());
	}
}
