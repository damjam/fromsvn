package com.ylink.cim.common.type;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractType;
import flink.util.ExceptionUtils;

/**
 * 定投周期
 * 
 */
public class AipPeriod extends AbstractType {

	public static Map<String, AipPeriod> ALL = new LinkedHashMap<String, AipPeriod>();

	public static final AipPeriod AIP_DAY = new AipPeriod("按日", "1");
	public static final AipPeriod AIP_WEEK = new AipPeriod("月均", "2");

	protected AipPeriod(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static AipPeriod valueOf(String value) throws Exception {

		AipPeriod type = ALL.get(value);
		if (null == type) {
			ExceptionUtils.logException(AipPeriod.class, "类型错误");
		}
		return type;
	}

	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("aipPeriods", AipPeriod.ALL.values());
	}
}
