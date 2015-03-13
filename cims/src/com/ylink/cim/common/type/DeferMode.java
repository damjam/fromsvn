package com.ylink.cim.common.type;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractState;
import flink.util.ExceptionUtils;

/**
 * 
 * ����˳��ģʽ
 * 
 */
public class DeferMode extends AbstractState {
	public static Map<String, DeferMode> ALL = new LinkedHashMap<String, DeferMode>();

	protected DeferMode(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static final DeferMode MODE_TRANSEXTENDED = new DeferMode("����˳��ģʽ", "1");
	public static final DeferMode MODE_FUNDDEDUCE = new DeferMode("�ʽ�ۻ�ģʽ", "2");

	public static DeferMode valueOf(String value) throws Exception {
		DeferMode mode = ALL.get(value);
		if (null == mode) {
			ExceptionUtils.logException(DeferMode.class, "���ʹ���");
		}
		return mode;
	}

	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("deferModes", DeferMode.ALL.values());
	}
}
