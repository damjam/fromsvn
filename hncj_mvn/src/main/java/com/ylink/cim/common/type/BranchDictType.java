package com.ylink.cim.common.type;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractType;
import flink.util.ExceptionUtils;

public class BranchDictType extends AbstractType {

	public static Map<String, BranchDictType> ALL = new LinkedHashMap<String, BranchDictType>();

	
	public static final BranchDictType HouseType = new BranchDictType("住宅区楼号", "HouseType");
	public static final BranchDictType FlatType = new BranchDictType("公寓区楼号", "FlatType");
	public static final BranchDictType CommType = new BranchDictType("商业区楼号", "CommType");
	
	protected BranchDictType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static BranchDictType valueOf(String value) throws Exception {

		BranchDictType sysDictType = ALL.get(value);
		if (null == sysDictType) {
			ExceptionUtils.logException(BranchDictType.class, "类型错误");
		}
		return sysDictType;
	}

	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("dictTypes", ALL.values());
	}

}
