package com.ylink.cim.common.type;

import java.util.LinkedHashMap;
import java.util.Map;

import flink.util.AbstractType;
import flink.util.ExceptionUtils;

public class BusiType extends AbstractType {

	public static Map<String, BusiType> ALL = new LinkedHashMap<String, BusiType>();

	private String code;
	public String getCode() {
		return code;
	}

	public static final BusiType GOLD = new BusiType("�ƽ�Ͷ", "00", "Au");
	//public static final BusiType EX1 = new BusiType("��չҵ��1", "01", "TD");
	//public static final BusiType EX2 = new BusiType("��չҵ��2", "02");
	protected BusiType(String name, String value, String code) {
		super(name, value);
		this.code = code;
		ALL.put(value, this);
	}

	public static BusiType valueOf(String value) throws Exception {

		BusiType type = ALL.get(value);
		if (null == type) {
			ExceptionUtils.logException(BusiType.class, "���ʹ���");
		}

		return type;
	}

}
