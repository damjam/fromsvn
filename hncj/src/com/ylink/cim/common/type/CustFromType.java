package com.ylink.cim.common.type;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractType;
import flink.util.ExceptionUtils;

public class CustFromType extends AbstractType {

	public static Map<String, CustFromType> ALL = new LinkedHashMap<String, CustFromType>();

	public static final CustFromType TYPE_0 = new CustFromType("本系统", "0");
	public static final CustFromType TYPE_1 = new CustFromType("金赢在线", "1");
	public static final CustFromType TYPE_2 = new CustFromType("其他", "2");

	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("custFromTypes", CustFromType.ALL.values());
	}

	public static CustFromType valueOf(String value) throws Exception {

		CustFromType type = ALL.get(value);
		if (null == type) {
			ExceptionUtils.logException(CustFromType.class, "类型错误");
		}
		return type;
	}

	protected CustFromType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
}
