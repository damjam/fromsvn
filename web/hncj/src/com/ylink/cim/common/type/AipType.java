package com.ylink.cim.common.type;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractType;
import flink.util.ExceptionUtils;

/**
 * 
 * ��Ͷ���
 * 
 */
public class AipType extends AbstractType {

	public static Map<String, AipType> ALL = new LinkedHashMap<String, AipType>();

	public static final AipType MODE_1 = new AipType("�վ���Ͷ", "1");
	public static final AipType MODE_2 = new AipType("ָ���ն�Ͷ", "2");

	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("aipTypes", AipType.ALL.values());
	}

	public static AipType valueOf(String value) throws Exception {

		AipType type = ALL.get(value);
		if (null == type) {
			ExceptionUtils.logException(AipType.class, "���ʹ���");
		}
		return type;
	}

	protected AipType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
}
