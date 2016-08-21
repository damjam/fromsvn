package com.ylink.cim.common.type;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractState;
import flink.util.ExceptionUtils;

/**
 * 
 * ί����Դ
 * 
 */
public class EntrSource extends AbstractState {
	public static Map<String, EntrSource> ALL = new LinkedHashMap<String, EntrSource>();

	protected EntrSource(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static final EntrSource INVESTPLAN = new EntrSource("��Ͷ�ƻ�", "1");
	public static final EntrSource TRADINGCOMMISSION = new EntrSource("����ί��", "2");
	public static final EntrSource POSTPONE = new EntrSource("˳��", "3");

	public static EntrSource valueOf(String value) throws Exception {
		EntrSource type = ALL.get(value);
		if (null == type) {
			ExceptionUtils.logException(EntrSource.class, "���ʹ���");
		}
		return type;
	}

	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("entrSources", EntrSource.ALL.values());
	}
}
