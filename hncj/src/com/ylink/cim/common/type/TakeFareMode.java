package com.ylink.cim.common.type;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractType;
import flink.util.ExceptionUtils;

/**
 * 
 * 实物黄金提取费模式
 * 
 */
public class TakeFareMode extends AbstractType {
	public static Map<String, TakeFareMode> ALL = new LinkedHashMap<String, TakeFareMode>();

	protected TakeFareMode(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static final TakeFareMode MODE_FIXED = new TakeFareMode("固定收费模式",
			"1");
	public static final TakeFareMode MODE_FLOATING = new TakeFareMode("浮动收费模式",
			"2");

	public static TakeFareMode valueOf(String value) throws Exception {
		TakeFareMode type = ALL.get(value);
		if (null == type) {
			ExceptionUtils.logException(TakeFareMode.class, "类型错误");
		}
		return type;
	}

	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("takeFareModes", TakeFareMode.ALL.values());
	}
}
