package com.ylink.cim.common.type;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractType;
import flink.util.ExceptionUtils;

public class PushType extends AbstractType {

	public static Map<String, PushType> ALL = new LinkedHashMap<String, PushType>();

	public static final PushType MOBILE = new PushType("短信", "0");

	public static final PushType MAIL = new PushType("邮件", "1");

	protected PushType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static PushType valueOf(String value) throws Exception {

		PushType type = ALL.get(value);
		if (null == type) {
			ExceptionUtils.logException(PushType.class, "类型错误");
		}
		return type;
	}

	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("pushTypes", PushType.ALL.values());
	}
}
