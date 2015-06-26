package com.ylink.cim.common.type;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractType;
import flink.util.ExceptionUtils;

public class UserType extends AbstractType {

	public static Map<String, UserType> ALL = new LinkedHashMap<String, UserType>();

	public static final UserType SUPER_ADMIN = new UserType("超级管理员", "superUser");
	public static final UserType SYS_ADMIN = new UserType("系统管理员", "adminUser");
	public static final UserType BSUI_OPER = new UserType("业务管理员", "busiUser");
	public static final UserType CUSTOM = new UserType("客户", "customUser");
	public static final UserType BRANCH_ADMIN_USER = new UserType("代理机构系统管理员", "branchAdminUser");

	protected UserType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static UserType valueOf(String value) throws Exception {

		UserType sysDictType = ALL.get(value);
		if (null == sysDictType) {
			ExceptionUtils.logException(UserType.class, "类型错误");
		}
		return sysDictType;
	}

	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("userTypes", UserType.ALL.values());
	}
}
