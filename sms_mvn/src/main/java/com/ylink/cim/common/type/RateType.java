package com.ylink.cim.common.type;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractType;
import flink.util.ExceptionUtils;

public class RateType extends AbstractType {

	public static Map<String, RateType> ALL = new LinkedHashMap<String, RateType>();

	public static final RateType SATISFIED = new RateType("满意", "0");
	public static final RateType IMP = new RateType("一般", "1");
	public static final RateType NOT = new RateType("不满意", "2");
	
	protected RateType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static RateType valueOf(String value) throws Exception {

		RateType sysDictType = ALL.get(value);
		if (null == sysDictType) {
			ExceptionUtils.logException(RateType.class, "类型错误");
		}
		return sysDictType;
	}

	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("rateTypes", ALL.values());
	}

}
