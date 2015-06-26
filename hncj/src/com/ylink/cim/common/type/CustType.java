package com.ylink.cim.common.type;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractType;
import flink.util.ExceptionUtils;

public class CustType extends AbstractType {

	public static Map<String, CustType> ALL = new LinkedHashMap<String, CustType>();

	public static final CustType TYPE_NORMAL = new CustType("普通客户", "0");
	public static final CustType TYPE_MEMBER = new CustType("会员客户", "1");

	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("custTypes", CustType.ALL.values());
	}

	public static CustType valueOf(String value) throws Exception {

		CustType type = ALL.get(value);
		if (null == type) {
			ExceptionUtils.logException(CustType.class, "类型错误");
		}
		return type;
	}

	protected CustType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
}
