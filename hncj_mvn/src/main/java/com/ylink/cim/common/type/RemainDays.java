package com.ylink.cim.common.type;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractType;

public class RemainDays extends AbstractType {
	public static Map<String, RemainDays> ALL = new LinkedHashMap<String, RemainDays>();

	public static final RemainDays LEFT_60 = new RemainDays("60天以内", "60");
	public static final RemainDays LEFT_30 = new RemainDays("30天以内", "30");
	public static final RemainDays LEFT_15 = new RemainDays("15天以内", "15");
	public static final RemainDays LEFT_7 = new RemainDays("7天以内", "7");

	protected RemainDays(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static RemainDays valueOf(String value) {
		RemainDays type = ALL.get(value);
		return type;
	}

	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("remainDays", RemainDays.ALL.values());
	}
}
