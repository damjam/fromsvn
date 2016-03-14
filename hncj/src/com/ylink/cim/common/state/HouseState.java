package com.ylink.cim.common.state;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractType;
import flink.util.ExceptionUtils;

/**
 * 
 * 房屋状态
 *
 */
public class HouseState extends AbstractType {
	public static Map<String, HouseState> ALL = new LinkedHashMap<String, HouseState>();
	//对精装公寓只存在
	public static final HouseState STATE_00 = new HouseState("未交房", "00");
	public static final HouseState STATE_01 = new HouseState("已交房未入住", "01");
	public static final HouseState STATE_02 = new HouseState("已入住", "02");

	protected HouseState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static HouseState valueOf(String value) throws Exception {

		HouseState state = ALL.get(value);
		if (null == state) {
			ExceptionUtils.logException(HouseState.class, "状态错误");
		}
		return state;
	}

	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("houseStates", HouseState.ALL.values());
	}
}
