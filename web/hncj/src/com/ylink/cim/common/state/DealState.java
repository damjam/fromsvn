package com.ylink.cim.common.state;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractType;
import flink.util.ExceptionUtils;

/**
 * 
 * 成交状态
 *
 */
public class DealState extends AbstractType {
	public static Map<String, DealState> ALL = new LinkedHashMap<String, DealState>();

	public static final DealState DEAL_STAT_NOT = new DealState("未成交", "1");
	public static final DealState DEAL_STAT_ENTIRELY = new DealState("成交", "2");
	public static final DealState DEAL_STAT_PART = new DealState("部分成交", "3");

	protected DealState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static DealState valueOf(String value) throws Exception {

		DealState state = ALL.get(value);
		if (null == state) {
			ExceptionUtils.logException(DealState.class, "状态错误");
		}
		return state;
	}

	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("dealStates", DealState.ALL.values());
	}
}
