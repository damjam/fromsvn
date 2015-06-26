package com.ylink.cim.common.state;

/**
 * 提货状态
 * 
 */
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractState;
import flink.util.ExceptionUtils;

public class TakeState extends AbstractState {

	public static Map<String, TakeState> ALL = new LinkedHashMap<String, TakeState>();

	protected TakeState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static final TakeState STATE_1 = new TakeState("申请提货", "1");
	public static final TakeState STATE_2 = new TakeState("客户撤销", "2");
	public static final TakeState STATE_3 = new TakeState("扣款成功", "3");
	public static final TakeState STATE_4 = new TakeState("扣款失败", "4");
	public static final TakeState STATE_5 = new TakeState("提货确认", "5");
	public static final TakeState STATE_6 = new TakeState("银行当日撤销", "6");
	public static final TakeState STATE_7 = new TakeState("银行次日撤销", "7");
	public static final TakeState STATE_8 = new TakeState("系统撤销", "8");
	public static final TakeState STATE_9 = new TakeState("已提货", "9");
	public static final TakeState STATE_10 = new TakeState("提货失败", "10");
	public static final TakeState STATE_11 = new TakeState("定投自动撤销", "11");

	public static TakeState valueOf(String value) throws Exception {
		TakeState state = ALL.get(value);
		if (null == state) {
			ExceptionUtils.logException(TakeState.class, "状态错误");
		}
		return state;
	}

	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("takeStates", TakeState.ALL.values());
	}
}
