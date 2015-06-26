package com.ylink.cim.common.state;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractState;
import flink.util.ExceptionUtils;

/**
 * 资金划转状态
 * 
 * @author
 * 
 */
public class FundState extends AbstractState {

	public static Map<String, FundState> ALL = new LinkedHashMap<String, FundState>();

	protected FundState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static final FundState STATE_1 = new FundState("指令申请", "1");
	public static final FundState STATE_2 = new FundState("指令撤销", "2");
	public static final FundState STATE_3 = new FundState("主机已发送", "3");
	public static final FundState STATE_4 = new FundState("主机处理成功", "4");
	public static final FundState STATE_5 = new FundState("主机处理失败", "5");
	public static final FundState STATE_6 = new FundState("主机处理超时", "6");
	public static final FundState STATE_7 = new FundState("清算行已发送", "7");
	public static final FundState STATE_8 = new FundState("清算行处理成功", "8");
	public static final FundState STATE_9 = new FundState("清算行处理失败", "9");
	public static final FundState STATE_10 = new FundState("已冲正", "10");

	public static FundState valueOf(String value) throws Exception {
		FundState fundState = ALL.get(value);
		if (null == fundState) {
			ExceptionUtils.logException(FundState.class, "状态错误");
		}
		return fundState;
	}

	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("fundStates", FundState.ALL.values());
	}

}
