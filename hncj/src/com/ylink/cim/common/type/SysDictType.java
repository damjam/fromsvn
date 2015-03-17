package com.ylink.cim.common.type;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractType;
import flink.util.ExceptionUtils;

public class SysDictType extends AbstractType {

	public static Map<String, SysDictType> ALL = new LinkedHashMap<String, SysDictType>();

	public static final SysDictType UserType = new SysDictType("用户类型", "UserType");
	public static final SysDictType BranchType = new SysDictType("机构类型", "BranchType");
	public static final SysDictType EconomicalType = new SysDictType("经济适用房楼号", "EconomicalType");
	public static final SysDictType RentType = new SysDictType("公租房楼号", "RentType");
	protected SysDictType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static SysDictType valueOf(String value) throws Exception {

		SysDictType sysDictType = ALL.get(value);
		if (null == sysDictType) {
			ExceptionUtils.logException(SysDictType.class, "类型错误");
		}
		return sysDictType;
	}
	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("dictTypes", ALL.values());
	}
	
}
