package com.ylink.cim.common.type;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractType;
import flink.util.ExceptionUtils;

/**
 * 
 * 扣款类型
 *
 */
public class DeductType extends AbstractType {
	public static Map<String, DeductType> ALL = new LinkedHashMap<String, DeductType>();

	public static final DeductType DEDUCT_TYPE_11 = new DeductType("交易委托流水", "11");
	public static final DeductType DEDUCT_TYPE_12 = new DeductType("提金准备金", "12");
	public static final DeductType DEDUCT_TYPE_23 = new DeductType("现金赎回划款", "23");
	public static final DeductType DEDUCT_TYPE_24 = new DeductType("入金", "24");
	public static final DeductType DEDUCT_TYPE_25 = new DeductType("出金", "25");
	public static final DeductType DEDUCT_TYPE_26 = new DeductType("溢短费返回", "26");
	public static final DeductType DEDUCT_TYPE_27 = new DeductType("主动定投划款", "27");
	public static final DeductType DEDUCT_TYPE_28 = new DeductType("补缴欠款", "28");
	protected DeductType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static DeductType valueOf(String value) throws Exception {

		DeductType type = ALL.get(value);
		if (null == type) {
			ExceptionUtils.logException(DeductType.class, "类型错误");
		}
		return type;
	}

	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("deductTypes", DeductType.ALL.values());
	}
}
