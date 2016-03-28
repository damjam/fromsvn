package com.ylink.cim.common.type;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractType;

public class OutputTradeType extends AbstractType {

	public static Map<String, OutputTradeType> ALL = new LinkedHashMap<String, OutputTradeType>();

	public static final OutputTradeType REFUND = new OutputTradeType("退押金",
			"10");
	public static final OutputTradeType ELECTRIC = new OutputTradeType("交电费",
			"11");
	public static final OutputTradeType SALARY = new OutputTradeType("支付工资",
			"12");
	public static final OutputTradeType DEVICE = new OutputTradeType("购买器材设备",
			"13");
	public static final OutputTradeType REPAIR = new OutputTradeType("支付维修费",
			"14");
	public static final OutputTradeType ADD_GAS = new OutputTradeType("加油",
			"15");
	public static final OutputTradeType WITHDRAW = new OutputTradeType(
			"退业主预存款", "16");
	public static final OutputTradeType INNER_WITHDRAW = new OutputTradeType(
			"内部提现", "17");
	public static final OutputTradeType OUT_OTHER = new OutputTradeType("其他",
			"19");

	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("tradeTypes", OutputTradeType.ALL.values());
	}

	public static OutputTradeType valueOf(String value) {
		OutputTradeType type = ALL.get(value);
		return type;
	}

	protected OutputTradeType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
}
