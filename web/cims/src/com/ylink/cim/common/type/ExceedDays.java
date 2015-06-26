package com.ylink.cim.common.type;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractType;

public class ExceedDays extends AbstractType {
	public static Map<String, ExceedDays> ALL = new LinkedHashMap<String, ExceedDays>();
	public static final ExceedDays LEFT_90 = new ExceedDays("90天以上", "90");
	public static final ExceedDays LEFT_60 = new ExceedDays("60天以上", "60");
	public static final ExceedDays LEFT_30 = new ExceedDays("30天以上", "30");
	public static final ExceedDays LEFT_15 = new ExceedDays("15天以上", "15");
	
	protected ExceedDays(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static ExceedDays valueOf(String value) {
		ExceedDays type = ALL.get(value);
		return type;
	}

	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("exceedDays", ExceedDays.ALL.values());
	}
}
