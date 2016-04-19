package com.ylink.cim.common.type;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractType;
import flink.util.ExceptionUtils;

public class SexType extends AbstractType {

	public static Map<String, SexType> ALL = new LinkedHashMap<String, SexType>();

	public static final SexType SEX_M = new SexType("��", "��");
	public static final SexType SEX_F = new SexType("Ů", "Ů");

	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("sexTypes", SexType.ALL.values());
	}

	public static SexType valueOf(String value) throws Exception {

		SexType type = ALL.get(value);
		if (null == type) {
			ExceptionUtils.logException(SexType.class, "���ʹ���");
		}
		return type;
	}

	protected SexType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
}
