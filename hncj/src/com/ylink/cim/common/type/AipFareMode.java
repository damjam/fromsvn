package com.ylink.cim.common.type;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractType;
import flink.util.ExceptionUtils;

/**
 * 
 * 费用模式
 * 
 */
public class AipFareMode extends AbstractType {
	public static Map<String, AipFareMode> ALL = new LinkedHashMap<String, AipFareMode>();

	public static final AipFareMode FAREMODE_1 = new AipFareMode("成交金额比率", "1");
	public static final AipFareMode FAREMODE_2 = new AipFareMode("重量(元/克)", "2");
	public static final AipFareMode FAREMODE_A = new AipFareMode("账户管理费(元/千克/天)", "A");

	protected AipFareMode(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static AipFareMode valueOf(String value) throws Exception {

		AipFareMode type = ALL.get(value);
		if (null == type) {
			ExceptionUtils.logException(AipFareMode.class, "类型错误");
		}
		return type;
	}

	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("aipFareModes", AipFareMode.ALL.values());
	}
}
