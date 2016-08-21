package com.ylink.cim.common.type;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractType;

public class OutputTradeType extends AbstractType {

	public static Map<String, OutputTradeType> ALL = new LinkedHashMap<String, OutputTradeType>();

	public static final OutputTradeType ELECTRIC = new OutputTradeType("电费", "电费");
	public static final OutputTradeType WATER_FEE = new OutputTradeType("水费", "水费");
	public static final OutputTradeType CLEAN_FEE = new OutputTradeType("卫生费", "卫生费");
	public static final OutputTradeType HOUSE_RENT = new OutputTradeType("房租", "房租");
	public static final OutputTradeType SALARY = new OutputTradeType("支付工资", "支付工资");
	public static final OutputTradeType MATERIAL = new OutputTradeType("购买原材料", "购买原材料");
	public static final OutputTradeType REPAIR = new OutputTradeType("设备维修维护费", "设备维修维护费");
	public static final OutputTradeType DEVICE = new OutputTradeType("购买设备", "购买设备");
	public static final OutputTradeType ADD_GAS = new OutputTradeType("加油", "加油");
	public static final OutputTradeType LEND_OUT = new OutputTradeType("对外借款", "对外借款");
	public static final OutputTradeType PUBLIC_SPEND = new OutputTradeType("行政支出", "行政支出");
	public static final OutputTradeType INNER_WITHDRAW = new OutputTradeType("清算提现", "清算提现");
	public static final OutputTradeType GOODS_REFUND = new OutputTradeType("退款", "退款");
	public static final OutputTradeType IN_REVERSE = new OutputTradeType("冲正交易", "冲正交易");
	public static final OutputTradeType NORMAL_OUT = new OutputTradeType("日常支出", "日常支出");
	public static final OutputTradeType OUT_OTHER = new OutputTradeType("其他支出", "其他支出");
	
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
