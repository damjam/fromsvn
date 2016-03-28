package com.ylink.cim.common.type;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractType;
import flink.util.ExceptionUtils;

public class RedeemType extends AbstractType {
	public static Map<String, RedeemType> ALL = new LinkedHashMap<String, RedeemType>();

	public static final RedeemType GOLD = new RedeemType("实物", "1");
	public static final RedeemType CASH = new RedeemType("现金", "2");

	protected RedeemType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static RedeemType valueOf(String value) throws Exception {

		RedeemType type = ALL.get(value);
		if (null == type) {
			ExceptionUtils.logException(RedeemType.class, "类型错误");
		}
		return type;
	}

	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("redeemTypes", RedeemType.ALL.values());
	}
}
