package com.ylink.cim.common.type;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractState;
import flink.util.ExceptionUtils;

/**
 * 
 * 交易顺延模式
 * 
 */
public class DeferMode extends AbstractState {
	public static Map<String, DeferMode> ALL = new LinkedHashMap<String, DeferMode>();

	public static final DeferMode MODE_TRANSEXTENDED = new DeferMode("交易顺延模式",
			"1");

	public static final DeferMode MODE_FUNDDEDUCE = new DeferMode("资金扣划模式", "2");

	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("deferModes", DeferMode.ALL.values());
	}

	public static DeferMode valueOf(String value) throws Exception {
		DeferMode mode = ALL.get(value);
		if (null == mode) {
			ExceptionUtils.logException(DeferMode.class, "类型错误");
		}
		return mode;
	}

	protected DeferMode(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
}
