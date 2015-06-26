package com.ylink.cim.common.type;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractType;
import flink.util.ExceptionUtils;

/**
 * 
 * ʵ��ƽ���ȡ��ģʽ
 * 
 */
public class TakeFareMode extends AbstractType {
	public static Map<String, TakeFareMode> ALL = new LinkedHashMap<String, TakeFareMode>();

	protected TakeFareMode(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static final TakeFareMode MODE_FIXED = new TakeFareMode("�̶��շ�ģʽ", "1");
	public static final TakeFareMode MODE_FLOATING = new TakeFareMode("�����շ�ģʽ", "2");

	public static TakeFareMode valueOf(String value) throws Exception {
		TakeFareMode type = ALL.get(value);
		if (null == type) {
			ExceptionUtils.logException(TakeFareMode.class, "���ʹ���");
		}
		return type;
	}

	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("takeFareModes", TakeFareMode.ALL.values());
	}
}
