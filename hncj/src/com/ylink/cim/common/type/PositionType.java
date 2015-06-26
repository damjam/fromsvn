package com.ylink.cim.common.type;

import java.util.LinkedHashMap;
import java.util.Map;

import flink.util.AbstractType;
import flink.util.ExceptionUtils;

public class PositionType extends AbstractType {
	public static Map<String, PositionType> ALL = new LinkedHashMap<String, PositionType>();

	public static final PositionType EAST = new PositionType("��", "1");
	public static final PositionType WEST = new PositionType("��", "2");
	public static final PositionType EAST_SOUTH = new PositionType("����", "3");
	public static final PositionType EAST_NORTH = new PositionType("����", "4");
	public static final PositionType WEST_SOUTH = new PositionType("����", "5");
	public static final PositionType WEST_NORTH = new PositionType("����", "6");
	protected PositionType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static PositionType valueOf(String value) throws Exception {

		PositionType type = ALL.get(value);
		if (null == type) {
			ExceptionUtils.logException(PositionType.class, "���ʹ���");
		}
		return type;
	}

}
