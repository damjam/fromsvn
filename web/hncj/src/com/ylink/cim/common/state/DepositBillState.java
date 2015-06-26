package com.ylink.cim.common.state;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractType;
import flink.util.ExceptionUtils;

public class DepositBillState extends AbstractType {
	public static Map<String, DepositBillState> ALL = new LinkedHashMap<String, DepositBillState>();

	public static final DepositBillState PAID = new DepositBillState("ÒÑ½ÉÄÉ", "00");
	public static final DepositBillState RETURN = new DepositBillState("ÒÑÍË»¹", "01");

	protected DepositBillState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static DepositBillState valueOf(String value) throws Exception {

		DepositBillState state = ALL.get(value);
		if (null == state) {
			ExceptionUtils.logException(DepositBillState.class, "×´Ì¬´íÎó");
		}
		return state;
	}

	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("billStates", DepositBillState.ALL.values());
	}
}
