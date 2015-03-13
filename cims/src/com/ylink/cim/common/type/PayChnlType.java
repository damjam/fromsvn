package com.ylink.cim.common.type;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractType;
import flink.util.ExceptionUtils;

public class PayChnlType extends AbstractType {

	public static Map<String, PayChnlType> ALL = new LinkedHashMap<String, PayChnlType>();

	public static final PayChnlType BANK_102 = new PayChnlType("�й���������", "102");
	public static final PayChnlType BANK_103 = new PayChnlType("�й�ũҵ����", "103");
	public static final PayChnlType BANK_104 = new PayChnlType("�й�����", "104");
	public static final PayChnlType BANK_105 = new PayChnlType("�й���������", "105");
	public static final PayChnlType BANK_301 = new PayChnlType("��ͨ����", "301");
	public static final PayChnlType BANK_302 = new PayChnlType("��������", "302");
	public static final PayChnlType BANK_303 = new PayChnlType("�й��������", "303");
	public static final PayChnlType BANK_304 = new PayChnlType("��������", "303");
	public static final PayChnlType BANK_305 = new PayChnlType("�й���������", "303");
	public static final PayChnlType BANK_306 = new PayChnlType("�㶫��չ����", "303");
	public static final PayChnlType BANK_307 = new PayChnlType("���ڷ�չ����", "307");
	public static final PayChnlType BANK_308 = new PayChnlType("��������", "308");
	public static final PayChnlType BANK_309 = new PayChnlType("��ҵ����", "309");
	public static final PayChnlType BANK_001 = new PayChnlType("��ݸ����", "001");
	public static final PayChnlType BANK_002 = new PayChnlType("��������", "002");
	
	protected PayChnlType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static PayChnlType valueOf(String value) throws Exception {

		PayChnlType type = ALL.get(value);
		if (null == type) {
			ExceptionUtils.logException(PayChnlType.class, "���ʹ���");
		}

		return type;
	}
	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("payChnlTypes", PayChnlType.ALL.values());
	}
}
