package com.ylink.cim.common.state;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractType;
import flink.util.ExceptionUtils;

/**
 * 
 * ����״̬
 *
 */
public class HouseState extends AbstractType {
	public static Map<String, HouseState> ALL = new LinkedHashMap<String, HouseState>();
	//�Ծ�װ��Ԣֻ����
	public static final HouseState STATE_00 = new HouseState("δ����", "00");
	public static final HouseState STATE_01 = new HouseState("�ѽ���", "01");

	protected HouseState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static HouseState valueOf(String value) throws Exception {

		HouseState state = ALL.get(value);
		if (null == state) {
			ExceptionUtils.logException(HouseState.class, "״̬����");
		}
		return state;
	}

	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("houseStates", HouseState.ALL.values());
	}
}
