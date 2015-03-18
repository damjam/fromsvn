package com.ylink.cim.common.state;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractState;
import flink.util.ExceptionUtils;

public class SignState extends AbstractState {

	public static Map<String, SignState> ALL = new LinkedHashMap<String, SignState>();

	protected SignState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static final SignState UNCONFIRM_ADD = new SignState("新增待确认", "0");
	public static final SignState SIGNED = new SignState("正常", "1");
	public static final SignState CANCELED_ACCT = new SignState("已销户", "2");
	public static final SignState UNCONFIRM_UPDATE = new SignState("更改待确认", "3");
	public static final SignState CANCELING_ACCT = new SignState("销户解约中", "4");
	public static final SignState CANCELING_SIGN = new SignState("更改账户绑定解约中", "5");
	public static final SignState UNSIGN = new SignState("未签约", "6");// 解约后

	// 无关联签约卡时
	public static SignState valueOf(String value) throws Exception {
		SignState state = ALL.get(value);
		if (null == state) {
			ExceptionUtils.logException(SignState.class, "状态错误");
		}
		return state;
	}

	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("signStates", SignState.ALL.values());
	}
}
