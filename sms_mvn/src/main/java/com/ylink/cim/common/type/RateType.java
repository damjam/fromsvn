package com.ylink.cim.common.type;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractType;
import flink.util.ExceptionUtils;

public class RateType extends AbstractType {

	public static Map<String, RateType> ALL = new LinkedHashMap<String, RateType>();

	public static final RateType SATISFIED = new RateType("����", "0");
	public static final RateType IMP = new RateType("һ��", "1");
	public static final RateType NOT = new RateType("������", "2");
	
	protected RateType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static RateType valueOf(String value) throws Exception {

		RateType sysDictType = ALL.get(value);
		if (null == sysDictType) {
			ExceptionUtils.logException(RateType.class, "���ʹ���");
		}
		return sysDictType;
	}

	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("rateTypes", ALL.values());
	}

}
