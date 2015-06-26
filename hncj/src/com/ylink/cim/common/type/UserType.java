package com.ylink.cim.common.type;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractType;
import flink.util.ExceptionUtils;

public class UserType extends AbstractType {

	public static Map<String, UserType> ALL = new LinkedHashMap<String, UserType>();

	public static final UserType SUPER_ADMIN = new UserType("��������Ա", "superUser");
	public static final UserType SYS_ADMIN = new UserType("ϵͳ����Ա", "adminUser");
	public static final UserType BSUI_OPER = new UserType("ҵ�����Ա", "busiUser");
	public static final UserType CUSTOM = new UserType("�ͻ�", "customUser");
	public static final UserType BRANCH_ADMIN_USER = new UserType("�������ϵͳ����Ա", "branchAdminUser");

	protected UserType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static UserType valueOf(String value) throws Exception {

		UserType sysDictType = ALL.get(value);
		if (null == sysDictType) {
			ExceptionUtils.logException(UserType.class, "���ʹ���");
		}
		return sysDictType;
	}

	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("userTypes", UserType.ALL.values());
	}
}
