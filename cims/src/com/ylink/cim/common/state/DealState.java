package com.ylink.cim.common.state;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractType;
import flink.util.ExceptionUtils;

/**
 * 
 * �ɽ�״̬
 *
 */
public class DealState extends AbstractType {
	public static Map<String, DealState> ALL = new LinkedHashMap<String, DealState>();

	public static final DealState DEAL_STAT_NOT = new DealState("δ�ɽ�", "1");
	public static final DealState DEAL_STAT_ENTIRELY = new DealState("�ɽ�", "2");
	public static final DealState DEAL_STAT_PART = new DealState("���ֳɽ�", "3");

	protected DealState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static DealState valueOf(String value) throws Exception {

		DealState state = ALL.get(value);
		if (null == state) {
			ExceptionUtils.logException(DealState.class, "״̬����");
		}
		return state;
	}

	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("dealStates", DealState.ALL.values());
	}
}
