package com.ylink.cim.common.type;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractType;

public class TradeType extends AbstractType {

	public static Map<String, TradeType> ALL = new LinkedHashMap<String, TradeType>();

	//public static final TradeType SERVICE = new TradeType("物业费(含公共能源费)", "00");
	//public static final TradeType WATER = new TradeType("水费", "01");
	//public static final TradeType DEPOSIT = new TradeType("业主充值", "02");
	//public static final TradeType SECURITY = new TradeType("收押金", "03");
	//public static final TradeType DECORATE = new TradeType("装修服务费", "04");
	//public static final TradeType PARKING = new TradeType("车位维护费", "05");
	//public static final TradeType CARD_FEE = new TradeType("智能卡办理费", "07");
	//public static final TradeType PASS_FEE = new TradeType("临时出入证办理费", "08");
	//public static final TradeType AD_RENT = new TradeType("广告位租赁费", "20");
	//public static final TradeType IC_DEPOSIT = new TradeType("IC卡充值", "21");
	public static final TradeType INNER_DEPOSIT = new TradeType("内部充值", "内部充值");
	public static final TradeType ORDER_INCOME = new TradeType("订单收入", "订单收入");
	public static final TradeType PRESTORE = new TradeType("客户定金", "客户定金");
	public static final TradeType IN_OTHER = new TradeType("其他收入", "其他收入");

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
