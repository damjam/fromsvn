package com.ylink.cim.common.type;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractType;

public class InputTradeType extends AbstractType {

	public static Map<String, InputTradeType> ALL = new LinkedHashMap<String, InputTradeType>();

	public static final InputTradeType SERVICE = new InputTradeType("物业费(含照明费)", "00");
	public static final InputTradeType WATER = new InputTradeType("收水费", "01");
	public static final InputTradeType DEPOSIT = new InputTradeType("业主充值", "02");
	public static final InputTradeType SECURITY = new InputTradeType("收押金", "03");
	public static final InputTradeType DECORATE = new InputTradeType("收装修服务费", "04");
	public static final InputTradeType PARKING = new InputTradeType("收停车费", "05");
	public static final InputTradeType INNER_DEPOSIT = new InputTradeType("内部充值", "06");
	public static final InputTradeType IN_OTHER = new InputTradeType("其他入账", "09");

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
