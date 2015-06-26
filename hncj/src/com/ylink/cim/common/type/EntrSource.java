package com.ylink.cim.common.type;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractState;
import flink.util.ExceptionUtils;

/**
 * 
 * 委托来源
 * 
 */
public class EntrSource extends AbstractState {
	public static Map<String, EntrSource> ALL = new LinkedHashMap<String, EntrSource>();

	protected EntrSource(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static final EntrSource INVESTPLAN = new EntrSource("定投计划", "1");
	public static final EntrSource TRADINGCOMMISSION = new EntrSource("买卖委托", "2");
	public static final EntrSource POSTPONE = new EntrSource("顺延", "3");

	public static EntrSource valueOf(String value) throws Exception {
		EntrSource type = ALL.get(value);
		if (null == type) {
			ExceptionUtils.logException(EntrSource.class, "类型错误");
		}
		return type;
	}

	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("entrSources", EntrSource.ALL.values());
	}
}
