package com.ylink.cim.common.type;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractType;
import flink.util.ExceptionUtils;

/**
 * 
 * 代客交易账户类型
 * 
 */
public class SubAcctType extends AbstractType {
	public static Map<String, SubAcctType> ALL = new LinkedHashMap<String, SubAcctType>();

	public static final SubAcctType ACCOUNT_TRADING = new SubAcctType("交易账户",
			"1");
	public static final SubAcctType ACCOUNT_ACTUAL = new SubAcctType("实物账户",
			"2");

	protected SubAcctType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static SubAcctType valueOf(String value) throws Exception {

		SubAcctType type = ALL.get(value);
		if (null == type) {
			ExceptionUtils.logException(SubAcctType.class, "类型错误");
		}
		return type;
	}

	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("subAcctTypes", SubAcctType.ALL.values());
	}
}
