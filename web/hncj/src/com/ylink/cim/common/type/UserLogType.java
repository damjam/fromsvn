package com.ylink.cim.common.type;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.etc.RuntimeBizException;
import flink.util.AbstractType;

/**
 * 
 */
public class UserLogType extends AbstractType {
	public static final Map<String, UserLogType> ALL = new LinkedHashMap<String, UserLogType>();

	public static final UserLogType SEARCH = new UserLogType("��ѯ", "S");
	public static final UserLogType ADD = new UserLogType("����", "A");
	public static final UserLogType UPDATE = new UserLogType("����", "U");
	public static final UserLogType DELETE = new UserLogType("ɾ��", "D");
	public static final UserLogType CHECK = new UserLogType("���", "C");
	public static final UserLogType OTHER = new UserLogType("����", "O");

	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("userLogTypes", UserLogType.ALL.values());
	}

	public static UserLogType valueOf(String value) {
		UserLogType type = (UserLogType) ALL.get(value);

		if (type == null) {
			throw new RuntimeBizException("�������ʹ���" + value);
		}

		return type;
	}

	protected UserLogType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

}
