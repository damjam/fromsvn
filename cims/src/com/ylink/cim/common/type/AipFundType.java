package com.ylink.cim.common.type;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractType;
import flink.util.ExceptionUtils;

/**
 * 
 * 定投款项
 * 
 */
public class AipFundType extends AbstractType {
	public static Map<String, AipFundType> ALL = new LinkedHashMap<String, AipFundType>();

	public static final AipFundType FUND_TYPE_1 = new AipFundType("买入货款", "1");
	public static final AipFundType FUND_TYPE_2 = new AipFundType("提货准备金", "2");
	public static final AipFundType FUND_TYPE_3 = new AipFundType("账户管理费", "3");
	public static final AipFundType FUND_TYPE_4 = new AipFundType("提金违约费", "4");
	public static final AipFundType FUND_TYPE_5 = new AipFundType("罚息", "5");

	protected AipFundType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static AipFundType valueOf(String value) throws Exception {

		AipFundType type = ALL.get(value);
		if (null == type) {
			ExceptionUtils.logException(AipFundType.class, "类型错误");
		}
		return type;
	}

	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("aipFundTypes", AipFundType.ALL.values());
	}
}
