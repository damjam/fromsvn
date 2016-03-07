package com.ylink.cim.common.state;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractState;
import flink.util.ExceptionUtils;

/**
 * 
 * ����״̬
 *
 */
public class MaterialState extends AbstractState {
	public static Map<String, MaterialState> ALL = new LinkedHashMap<String, MaterialState>();

	public static final MaterialState NORMAL = new MaterialState("����", "00");
	public static final MaterialState DAMAGED = new MaterialState("��", "01");
	public static final MaterialState SCRAPPED = new MaterialState("����", "02");
	public static final MaterialState LOST = new MaterialState("��ʧ", "03");

	protected MaterialState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static MaterialState valueOf(String value) throws Exception {

		MaterialState state = ALL.get(value);
		if (null == state) {
			ExceptionUtils.logException(MaterialState.class, "״̬����");
		}
		return state;
	}

	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("materialStates", MaterialState.ALL.values());
	}
}
