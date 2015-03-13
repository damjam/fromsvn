package com.ylink.cim.common.type;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractType;
import flink.util.ExceptionUtils;

/**
 * 
 * ί����Ա���
 * 
 */
public class AipStaffType extends AbstractType {
	public static Map<String, AipStaffType> ALL = new LinkedHashMap<String, AipStaffType>();

	public static final AipStaffType TELLER = new AipStaffType("��Ա����Ա��", "1");
	public static final AipStaffType CUSTOMER = new AipStaffType("�ͻ�", "2");

	protected AipStaffType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static AipStaffType valueOf(String value) throws Exception {

		AipStaffType type = ALL.get(value);
		if (null == type) {
			ExceptionUtils.logException(AipStaffType.class, "���ʹ���");
		}
		return type;
	}

	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("aipStaffTypes", AipStaffType.ALL.values());
	}
}
