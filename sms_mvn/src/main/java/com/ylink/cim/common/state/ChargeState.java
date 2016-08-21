package com.ylink.cim.common.state;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractState;
import flink.util.ExceptionUtils;

/**
 * 扣款状态
 * 
 * @author
 *
 */
public class ChargeState extends AbstractState {

	public static Map<String, ChargeState> ALL = new LinkedHashMap<String, ChargeState>();

	protected ChargeState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static final ChargeState NOT = new ChargeState("未扣款", "1");
	public static final ChargeState NORMAL = new ChargeState("正常扣款", "2");
	public static final ChargeState PART = new ChargeState("部分扣款", "3");
	public static final ChargeState FAIL = new ChargeState("扣款失败", "4");

	public static ChargeState valueOf(String value) throws Exception {
		ChargeState state = ALL.get(value);
		if (null == state) {
			ExceptionUtils.logException(ChargeState.class, "状态错误");
		}
		return state;
	}

	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("chargeStates", ChargeState.ALL.values());
	}
}
