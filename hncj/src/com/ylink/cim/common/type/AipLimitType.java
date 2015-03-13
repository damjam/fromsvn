package com.ylink.cim.common.type;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractType;
import flink.util.ExceptionUtils;

/**
 * 
 * ��Ͷ���׶���������
 * 
 */
public class AipLimitType extends AbstractType {
	public static Map<String, AipLimitType> ALL = new LinkedHashMap<String, AipLimitType>();

	public static final AipLimitType BUY_BY_MONEY = new AipLimitType("���������(Ԫ)", "1");
	public static final AipLimitType BUY_BY_WEIGHT = new AipLimitType("����������(��)", "2");
	public static final AipLimitType SELL_BY_WEIGHT = new AipLimitType("����������(��)", "3");
	public static final AipLimitType DELIVERYAPPLY_BY_WEIGHT = new AipLimitType("�����������(��)", "4");
	public static final AipLimitType INVENTORY_TOTAL_WEIGHT = new AipLimitType("���������(��)", "5");

	protected AipLimitType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static AipLimitType valueOf(String value) throws Exception {

		AipLimitType type = ALL.get(value);
		if (null == type) {
			ExceptionUtils.logException(AipLimitType.class, "���ʹ���");
		}
		return type;
	}

	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("aipLimitTypes", AipLimitType.ALL.values());
	}
}
