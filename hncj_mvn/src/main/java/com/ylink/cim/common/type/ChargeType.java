package com.ylink.cim.common.type;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractType;
import flink.util.ExceptionUtils;

public class ChargeType extends AbstractType {

	public static Map<String, ChargeType> ALL = new LinkedHashMap<String, ChargeType>();

	public static final ChargeType COMMON_SERVICE = new ChargeType("物业费", "00");
	public static final ChargeType LIFT_SERVICE = new ChargeType("电梯上料费", "01");
	public static final ChargeType JUNK_CLEAN = new ChargeType("垃圾清运费", "02");
	public static final ChargeType PUBLIC_ENERGE = new ChargeType("公共能源费", "03");
	public static final ChargeType LIFT_USE = new ChargeType("电梯使用费", "04");

	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("chargeTypes", ChargeType.ALL.values());
	}

	public static ChargeType valueOf(String value) throws Exception {

		ChargeType type = ALL.get(value);
		if (null == type) {
			ExceptionUtils.logException(ChargeType.class, "类型错误");
		}
		return type;
	}

	protected ChargeType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
}
