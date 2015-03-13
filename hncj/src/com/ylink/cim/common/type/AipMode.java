package com.ylink.cim.common.type;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractType;
import flink.util.ExceptionUtils;

/**
 * 
 * ��Ͷģʽ
 * 
 */
public class AipMode extends AbstractType {

	public static Map<String, AipMode> ALL = new LinkedHashMap<String, AipMode>();

	public static final AipMode MODE_1 = new AipMode("���", "1");
	public static final AipMode MODE_2 = new AipMode("����", "2");

	protected AipMode(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static AipMode valueOf(String value) throws Exception {

		AipMode type = ALL.get(value);
		if (null == type) {
			ExceptionUtils.logException(AipMode.class, "���ʹ���");
		}
		return type;
	}

	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("aipModes", AipMode.ALL.values());
	}
}
