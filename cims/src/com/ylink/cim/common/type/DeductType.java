package com.ylink.cim.common.type;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractType;
import flink.util.ExceptionUtils;

/**
 * 
 * �ۿ�����
 *
 */
public class DeductType extends AbstractType {
	public static Map<String, DeductType> ALL = new LinkedHashMap<String, DeductType>();

	public static final DeductType DEDUCT_TYPE_11 = new DeductType("����ί����ˮ", "11");
	public static final DeductType DEDUCT_TYPE_12 = new DeductType("���׼����", "12");
	public static final DeductType DEDUCT_TYPE_23 = new DeductType("�ֽ���ػ���", "23");
	public static final DeductType DEDUCT_TYPE_24 = new DeductType("���", "24");
	public static final DeductType DEDUCT_TYPE_25 = new DeductType("����", "25");
	public static final DeductType DEDUCT_TYPE_26 = new DeductType("��̷ѷ���", "26");
	public static final DeductType DEDUCT_TYPE_27 = new DeductType("������Ͷ����", "27");
	public static final DeductType DEDUCT_TYPE_28 = new DeductType("����Ƿ��", "28");
	protected DeductType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static DeductType valueOf(String value) throws Exception {

		DeductType type = ALL.get(value);
		if (null == type) {
			ExceptionUtils.logException(DeductType.class, "���ʹ���");
		}
		return type;
	}

	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("deductTypes", DeductType.ALL.values());
	}
}
