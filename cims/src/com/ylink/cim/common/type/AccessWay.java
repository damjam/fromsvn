package com.ylink.cim.common.type;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractType;
import flink.util.ExceptionUtils;

public class AccessWay extends AbstractType {

	public static Map<String, AccessWay> ALL = new LinkedHashMap<String, AccessWay>();

	public static final AccessWay DEPOSIT = new AccessWay("入金", "1");
	public static final AccessWay WITHDRAW = new AccessWay("出金", "2");

	protected AccessWay(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static AccessWay valueOf(String value) throws Exception {

		AccessWay type = ALL.get(value);
		if (null == type) {
			ExceptionUtils.logException(AccessWay.class, "类型错误");
		}
		return type;
	}

	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("accessWays", AccessWay.ALL.values());
	}
}
