package com.ylink.cim.common.type;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractType;

public class InputTradeType extends AbstractType {

	public static Map<String, InputTradeType> ALL = new LinkedHashMap<String, InputTradeType>();

	public static final InputTradeType INNER_DEPOSIT = new InputTradeType("内部充值", "内部充值");
	public static final InputTradeType ORDER_INCOME = new InputTradeType("订单收入", "订单收入");
	public static final InputTradeType PRESTORE = new InputTradeType("客户定金", "客户定金");
	public static final InputTradeType IN_OTHER = new InputTradeType("其他收入", "其他收入");

	protected InputTradeType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static InputTradeType valueOf(String value) {
		InputTradeType type = ALL.get(value);
		return type;
	}

	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("tradeTypes", InputTradeType.ALL.values());
	}
}
