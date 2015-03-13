package com.ylink.cim.common.type;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractType;
import flink.util.ExceptionUtils;

/**
 * 
 * 定投类别
 * 
 */
public class AipType extends AbstractType {

	public static Map<String, AipType> ALL = new LinkedHashMap<String, AipType>();

	public static final AipType MODE_1 = new AipType("日均定投", "1");
	public static final AipType MODE_2 = new AipType("指定日定投", "2");

	protected AipType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static AipType valueOf(String value) throws Exception {

		AipType type = ALL.get(value);
		if (null == type) {
			ExceptionUtils.logException(AipType.class, "类型错误");
		}
		return type;
	}

	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("aipTypes", AipType.ALL.values());
	}
}
