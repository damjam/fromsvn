package com.ylink.cim.common.type;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractType;
import flink.util.ExceptionUtils;

public class OwnerGrade extends AbstractType {

	public static Map<String, OwnerGrade> ALL = new LinkedHashMap<String, OwnerGrade>();

	public static final OwnerGrade NORMAL = new OwnerGrade("一般", "0");
	public static final OwnerGrade VIP = new OwnerGrade("重要", "1");
	public static final OwnerGrade VVIP = new OwnerGrade("非常重要", "2");

	protected OwnerGrade(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static OwnerGrade valueOf(String value) throws Exception {

		OwnerGrade type = ALL.get(value);
		if (null == type) {
			ExceptionUtils.logException(OwnerGrade.class, "类型错误");
		}
		return type;
	}

	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("ownerGrades", OwnerGrade.ALL.values());
	}
}
