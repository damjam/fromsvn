package com.ylink.cim.common.type;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractType;

public class OutputTradeType extends AbstractType {

	public static Map<String, OutputTradeType> ALL = new LinkedHashMap<String, OutputTradeType>();

	public static final TradeType ELECTRIC = new TradeType("电费", "电费");
	public static final TradeType WATER_FEE = new TradeType("水费", "水费");
	public static final TradeType CLEAN_FEE = new TradeType("卫生费", "卫生费");
	public static final TradeType HOUSE_RENT = new TradeType("房租", "房租");
	public static final TradeType SALARY = new TradeType("支付工资", "支付工资");
	public static final TradeType MATERIAL = new TradeType("购买原材料", "购买原材料");
	public static final TradeType REPAIR = new TradeType("设备维修维护费", "设备维修维护费");
	public static final TradeType DEVICE = new TradeType("购买设备", "购买设备");
	public static final TradeType ADD_GAS = new TradeType("加油", "加油");
	public static final TradeType LEND_OUT = new TradeType("对外借款", "对外借款");
	public static final TradeType PUBLIC_SPEND = new TradeType("行政支出", "行政支出");
	public static final TradeType INNER_WITHDRAW = new TradeType("清算提现", "清算提现");
	public static final TradeType GOODS_REFUND = new TradeType("退款", "退款");
	public static final TradeType IN_REVERSE = new TradeType("冲正交易", "冲正交易");
	public static final TradeType NORMAL_OUT = new TradeType("日常支出", "日常支出");
	public static final TradeType OUT_OTHER = new TradeType("其他支出", "其他支出");
	
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
