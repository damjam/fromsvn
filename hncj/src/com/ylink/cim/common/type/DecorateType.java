package com.ylink.cim.common.type;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractType;
import flink.util.ExceptionUtils;

public class DecorateType extends AbstractType {
	public static Map<String, DecorateType> ALL = new LinkedHashMap<String, DecorateType>();

	public static final DecorateType SIMPLE = new DecorateType("简装", "00");
	public static final DecorateType REFIND = new DecorateType("精装", "01");

	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("decorateTypes", DecorateType.ALL.values());
	}

	public static DecorateType valueOf(String value) throws Exception {

		DecorateType type = ALL.get(value);
		if (null == type) {
			ExceptionUtils.logException(DecorateType.class, "类型错误");
		}
		return type;
	}

	protected DecorateType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
}
