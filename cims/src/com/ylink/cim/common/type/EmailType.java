package com.ylink.cim.common.type;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractType;
import flink.util.ExceptionUtils;

public class EmailType extends AbstractType {
	public static Map<String, EmailType> ALL = new LinkedHashMap<String, EmailType>();

	public static final EmailType TYPE_163 = new EmailType("@163", "@163");
	public static final EmailType TYPE_SINA_CN = new EmailType("@sina.cn", "@sina.cn");
	public static final EmailType TYPE_SINA_COM = new EmailType("@sina.com", "@sina.com");

	protected EmailType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static EmailType valueOf(String value) throws Exception {

		EmailType type = ALL.get(value);
		if (null == type) {
			ExceptionUtils.logException(IdCardType.class, "¿‡–Õ¥ÌŒÛ");
		}

		return type;
	}

	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("emailTypes", EmailType.ALL.values());
	}
}
