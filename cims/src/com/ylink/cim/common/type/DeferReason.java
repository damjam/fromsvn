package com.ylink.cim.common.type;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractType;
import flink.util.ExceptionUtils;

/**
 * 
 * ˳��ԭ��
 *
 */
public class DeferReason extends AbstractType {
	public static Map<String, DeferReason> ALL = new LinkedHashMap<String, DeferReason>();

	public static final DeferReason LACK_OF_FUNDS = new DeferReason("�ͻ��ʽ���", "1");
	public static final DeferReason NOT_SUCCESSFULLY_COMPLETED = new DeferReason("�����޷�˳�����", "2");
	public static final DeferReason OTHER = new DeferReason("����", "3");

	protected DeferReason(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static DeferReason valueOf(String value) throws Exception {

		DeferReason type = ALL.get(value);
		if (null == type) {
			ExceptionUtils.logException(DeferReason.class, "���ʹ���");
		}
		return type;
	}

	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("deferReasons", DeferReason.ALL.values());
	}
}
