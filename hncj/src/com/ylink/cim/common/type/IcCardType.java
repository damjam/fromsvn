package com.ylink.cim.common.type;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractType;

public class IcCardType extends AbstractType {
	public static Map<String, IcCardType> ALL = new LinkedHashMap<String, IcCardType>();

	public static final IcCardType ELEC = new IcCardType("µç¿¨", "00");
	public static final IcCardType GAS = new IcCardType("È¼Æø¿¨", "01");
	public static final IcCardType WATER = new IcCardType("Ë®¿¨", "02");
	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("icCardTypes", IcCardType.ALL.values());
	}

	public static IcCardType valueOf(String value)  {
		IcCardType type = ALL.get(value);
		return type;
	}

	protected IcCardType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
}
