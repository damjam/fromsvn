package com.ylink.cim.common.type;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.etc.RuntimeBizException;
import flink.util.AbstractType;

/**
 * 
 */
public class LogClassType extends AbstractType {
	public static final Map<String, LogClassType> ALL = new LinkedHashMap<String, LogClassType>();

	public static final LogClassType SEARCH = new LogClassType("��Ϣ", "I");
	public static final LogClassType ADD = new LogClassType("����", "W");
	public static final LogClassType UPDATE = new LogClassType("����", "E");

	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("logClassTypes", LogClassType.ALL.values());
	}

	public static LogClassType valueOf(String value) {
		LogClassType type = ALL.get(value);
		if (type == null) {
			throw new RuntimeBizException("�������ʹ���" + value);
		}
		return type;
	}

	protected LogClassType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

}