package com.ylink.cim.common.type;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractType;
import flink.util.ExceptionUtils;

public class YesNoType extends AbstractType {
	public static Map<String, YesNoType> ALL = new LinkedHashMap<String, YesNoType>();

	public static final YesNoType YES = new YesNoType(" «", "1");
	public static final YesNoType NO = new YesNoType("∑Ò", "0");

	protected YesNoType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static YesNoType valueOf(String value) throws Exception {

		YesNoType type = ALL.get(value);
		if (null == type) {
			ExceptionUtils.logException(YesNoType.class, "¿‡–Õ¥ÌŒÛ");
		}
		return type;
	}

	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("yesNos", YesNoType.ALL.values());
	}
}
