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

	public static final UserLogType SEARCH = new UserLogType("查询", "S");
	public static final UserLogType ADD = new UserLogType("新增", "A");
	public static final UserLogType UPDATE = new UserLogType("更新", "U");
	public static final UserLogType DELETE = new UserLogType("删除", "D");
	public static final UserLogType CHECK = new UserLogType("审核", "C");
	public static final UserLogType OTHER = new UserLogType("其他", "O");

	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("userLogTypes", UserLogType.ALL.values());
	}

	public static UserLogType valueOf(String value) {
		UserLogType type = (UserLogType) ALL.get(value);

		if (type == null) {
			throw new RuntimeBizException("操作类型错误" + value);
		}

		return type;
	}

	protected UserLogType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

}
