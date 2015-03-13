package com.ylink.cim.common.type;

import java.util.LinkedHashMap;
import java.util.Map;

import flink.util.AbstractType;
import flink.util.ExceptionUtils;

public class PositionType extends AbstractType {
	public static Map<String, PositionType> ALL = new LinkedHashMap<String, PositionType>();

	public static final PositionType EAST = new PositionType("东", "1");
	public static final PositionType WEST = new PositionType("西", "2");
	public static final PositionType EAST_SOUTH = new PositionType("东南", "3");
	public static final PositionType EAST_NORTH = new PositionType("东北", "4");
	public static final PositionType WEST_SOUTH = new PositionType("西南", "5");
	public static final PositionType WEST_NORTH = new PositionType("西北", "6");
	protected PositionType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static PositionType valueOf(String value) throws Exception {

		PositionType type = ALL.get(value);
		if (null == type) {
			ExceptionUtils.logException(PositionType.class, "类型错误");
		}
		return type;
	}

}
