package com.ylink.cim.common.type;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractType;

public class TradeType extends AbstractType {

	public static Map<String, TradeType> ALL = new LinkedHashMap<String, TradeType>();
	
	public static final TradeType SERVICE = new TradeType("物业费(含公共能源费)", "00");
	public static final TradeType WATER = new TradeType("水费", "01");
	public static final TradeType DEPOSIT = new TradeType("业主充值", "02");
	public static final TradeType SECURITY = new TradeType("收押金", "03");
	public static final TradeType DECORATE = new TradeType("装修服务费", "04");
	public static final TradeType PARKING = new TradeType("车位维护费", "05");
	public static final TradeType INNER_DEPOSIT = new TradeType("内部充值", "06");
	public static final TradeType IN_OTHER = new TradeType("其他收入", "09");
	
	public static final TradeType REFUND = new TradeType("退押金", "10");
	public static final TradeType ELECTRIC = new TradeType("交电费", "11");
	public static final TradeType SALARY = new TradeType("支付工资", "12");
	public static final TradeType DEVICE = new TradeType("购买器材设备", "13");
	public static final TradeType REPAIR = new TradeType("支付维修费", "14");
	public static final TradeType ADD_GAS = new TradeType("加油", "15");
	public static final TradeType WITHDRAW = new TradeType("退业主预存款", "16");
	public static final TradeType INNER_WITHDRAW = new TradeType("清算提现", "17");
	public static final TradeType IN_REVERSE = new TradeType("冲正交易", "18");
	public static final TradeType OUT_OTHER = new TradeType("其他支出", "19");
	
	protected TradeType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static TradeType valueOf(String value) {
		TradeType type = ALL.get(value);
		return type;
	}
	
	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("tradeTypes", TradeType.ALL.values());
	}
}
