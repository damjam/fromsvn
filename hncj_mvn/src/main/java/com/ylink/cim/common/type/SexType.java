package com.ylink.cim.common.type;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractType;
import flink.util.ExceptionUtils;

public class SexType extends AbstractType {

	public static Map<String, SexType> ALL = new LinkedHashMap<String, SexType>();

	public static final SexType SEX_M = new SexType("男", "男");
	public static final SexType SEX_F = new SexType("女", "女");

	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("sexTypes", SexType.ALL.values());
	}

	public static SexType valueOf(String value) throws Exception {

		SexType type = ALL.get(value);
		if (null == type) {
			ExceptionUtils.logException(SexType.class, "类型错误");
		}
		return type;
	}

	protected SexType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
}
