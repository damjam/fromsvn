package com.ylink.cim.common.type;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractType;
import flink.util.ExceptionUtils;

public class ImpIndexType extends AbstractType {

	public static Map<String, ImpIndexType> ALL = new LinkedHashMap<String, ImpIndexType>();

	public static final ImpIndexType NORMAL = new ImpIndexType("һ��", "1");
	public static final ImpIndexType IMP = new ImpIndexType("����", "2");
	public static final ImpIndexType NOT = new ImpIndexType("������", "0");
	
	protected ImpIndexType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static ImpIndexType valueOf(String value) throws Exception {

		ImpIndexType sysDictType = ALL.get(value);
		if (null == sysDictType) {
			ExceptionUtils.logException(ImpIndexType.class, "���ʹ���");
		}
		return sysDictType;
	}

	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("emgIndexTypes", ALL.values());
	}

}
