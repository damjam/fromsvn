package com.ylink.cim.common.type;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractType;
import flink.util.ExceptionUtils;

public class InoutType extends AbstractType {

	public static Map<String, InoutType> ALL = new LinkedHashMap<String, InoutType>();

	public static final InoutType TYPE_IN = new InoutType("����", "0");
	public static final InoutType TYPE_OUT = new InoutType("֧��", "1");

	protected InoutType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static InoutType valueOf(String value) throws Exception {

		InoutType type = ALL.get(value);
		if (null == type) {
			ExceptionUtils.logException(InoutType.class, "���ʹ���");
		}
		return type;
	}

	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("inoutTypes", InoutType.ALL.values());
	}
}
