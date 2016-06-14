package com.ylink.cim.common.state;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractState;
import flink.util.ExceptionUtils;

public class AccountState extends AbstractState {
	public static Map<String, AccountState> ALL = new LinkedHashMap<String, AccountState>();

	public static final AccountState NORMAL = new AccountState("Õý³£", "00");
	public static final AccountState WRITTEN_OFF = new AccountState("×¢Ïú", "09");
	
	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("accountStates", AccountState.ALL.values());
	}

	public static AccountState valueOf(String value) throws Exception {

		AccountState state = ALL.get(value);
		if (null == state) {
			ExceptionUtils.logException(AccountState.class, "×´Ì¬´íÎó");
		}
		return state;
	}

	protected AccountState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
}
