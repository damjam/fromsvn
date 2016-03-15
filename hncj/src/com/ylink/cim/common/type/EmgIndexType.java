package com.ylink.cim.common.type;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractType;
import flink.util.ExceptionUtils;

public class EmgIndexType extends AbstractType {

	public static Map<String, EmgIndexType> ALL = new LinkedHashMap<String, EmgIndexType>();

	public static final EmgIndexType NORMAL = new EmgIndexType("һ��", "1");
	public static final EmgIndexType IMP = new EmgIndexType("��Ҫ", "2");
	public static final EmgIndexType NOT = new EmgIndexType("����Ҫ", "0");
	
	protected EmgIndexType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static EmgIndexType valueOf(String value) throws Exception {

		EmgIndexType sysDictType = ALL.get(value);
		if (null == sysDictType) {
			ExceptionUtils.logException(EmgIndexType.class, "���ʹ���");
		}
		return sysDictType;
	}

	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("impIndexTypes", ALL.values());
	}

}