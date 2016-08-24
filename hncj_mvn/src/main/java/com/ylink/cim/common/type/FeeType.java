package com.ylink.cim.common.type;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractType;
import flink.util.ExceptionUtils;

public class FeeType extends AbstractType {

	public static Map<String, FeeType> ALL = new LinkedHashMap<String, FeeType>();

	public static final FeeType MANAGE = new FeeType("物业费", "00");
	public static final FeeType LIGHTING = new FeeType("公共照明费", "01");
	public static final FeeType WATER = new FeeType("水费", "02");
	public static final FeeType ELECTIRC = new FeeType("电费", "03");
	public static final FeeType CLEAN = new FeeType("装修物料清运费", "04");
	public static final FeeType LIFT = new FeeType("装修电梯使用费", "05");
	public static final FeeType DEPOSIT = new FeeType("装修押金", "06");
	public static final FeeType WITHDRAW = new FeeType("退装修押金", "07");
	public static final FeeType MAINTAIN = new FeeType("维修基金", "08");
	public static final FeeType PARKING = new FeeType("车位租用费", "09");
	public static final FeeType OTHER = new FeeType("其他", "99");

	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("custTypes", FeeType.ALL.values());
	}

	public static FeeType valueOf(String value) throws Exception {

		FeeType type = ALL.get(value);
		if (null == type) {
			ExceptionUtils.logException(FeeType.class, "类型错误");
		}
		return type;
	}

	protected FeeType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
}
