package com.ylink.cim.common.type;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractType;
import flink.util.ExceptionUtils;

public class NumberTrans extends AbstractType {
	public static Map<String, NumberTrans> ALL = new LinkedHashMap<String, NumberTrans>();

	public static final NumberTrans NUM_1 = new NumberTrans("一", "1");
	public static final NumberTrans NUM_2 = new NumberTrans("二", "2");
	public static final NumberTrans NUM_3 = new NumberTrans("三", "3");
	public static final NumberTrans NUM_4 = new NumberTrans("四", "4");
	public static final NumberTrans NUM_5 = new NumberTrans("五", "5");
	public static final NumberTrans NUM_6 = new NumberTrans("六", "6");
	public static final NumberTrans NUM_7 = new NumberTrans("七", "7");
	public static final NumberTrans NUM_8 = new NumberTrans("八", "8");
	public static final NumberTrans NUM_9 = new NumberTrans("九", "9");
	public static final NumberTrans NUM_10 = new NumberTrans("十", "10");
	public static final NumberTrans NUM_11 = new NumberTrans("十一", "11");
	public static final NumberTrans NUM_12 = new NumberTrans("十二", "12");
	public static final NumberTrans NUM_13 = new NumberTrans("十三", "13");
	public static final NumberTrans NUM_14 = new NumberTrans("十四", "14");
	public static final NumberTrans NUM_15 = new NumberTrans("十五", "15");
	public static final NumberTrans NUM_16 = new NumberTrans("十六", "16");
	public static final NumberTrans NUM_17 = new NumberTrans("十七", "17");
	public static final NumberTrans NUM_18 = new NumberTrans("十八", "18");
	public static final NumberTrans NUM_19 = new NumberTrans("十九", "19");
	public static final NumberTrans NUM_20 = new NumberTrans("二十", "20");
	protected NumberTrans(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static NumberTrans valueOf(String value) throws Exception {

		NumberTrans type = ALL.get(value);
		if (null == type) {
			ExceptionUtils.logException(NumberTrans.class, "类型错误");
		}
		return type;
	}

	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("aipChanels", NumberTrans.ALL.values());
	}
}
