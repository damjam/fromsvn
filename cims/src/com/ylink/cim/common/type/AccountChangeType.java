package com.ylink.cim.common.type;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractType;
import flink.util.ExceptionUtils;

public class AccountChangeType extends AbstractType {

	public static Map<String, AccountChangeType> ALL = new LinkedHashMap<String, AccountChangeType>();

	public static final AccountChangeType WATER_FEE = new AccountChangeType("扣水费", "00");
	
	public static final AccountChangeType DEPOSIT = new AccountChangeType("充值", "01");

	public static final AccountChangeType WITHDRAW = new AccountChangeType("提现", "02");
	
	
	protected AccountChangeType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static AccountChangeType valueOf(String value) throws Exception {

		AccountChangeType type = ALL.get(value);
		if (null == type) {
			ExceptionUtils.logException(AccountChangeType.class, "类型错误");
		}
		return type;
	}

	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("changeTypes", AccountChangeType.ALL.values());
	}
}
