package com.ylink.cim.common.type;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractType;
import flink.util.ExceptionUtils;

public class ParamRange extends AbstractType {

	/**
	 * 
	 */
	public static Map<String, ParamRange> ALL = new LinkedHashMap<String, ParamRange>();

	public static final ParamRange BUILD = new ParamRange("¥", "01");
	public static final ParamRange UNIT = new ParamRange("��Ԫ", "02");
	public static final ParamRange FLOOR = new ParamRange("��", "03");
	public static final ParamRange HOUSE = new ParamRange("��", "04");

	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("paramRanges", ParamRange.ALL.values());
	}

	public static ParamRange valueOf(String value) throws Exception {

		ParamRange type = ALL.get(value);
		if (null == type) {
			ExceptionUtils.logException(ParamRange.class, "���ʹ���");
		}
		return type;
	}

	protected ParamRange(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
}
