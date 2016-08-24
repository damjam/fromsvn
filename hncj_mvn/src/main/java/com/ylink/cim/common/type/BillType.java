package com.ylink.cim.common.type;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractType;

public class BillType extends AbstractType {
	public static Map<String, BillType> ALL = new LinkedHashMap<String, BillType>();

	public static final BillType SERVICE = new BillType("物业费", "00");
	public static final BillType PARKING = new BillType("停车费", "05");

	protected BillType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static BillType valueOf(String value) {
		BillType type = ALL.get(value);
		return type;
	}

	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("billTypes", BillType.ALL.values());
	}
}
