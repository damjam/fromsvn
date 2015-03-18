package com.ylink.cim.common.type;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractType;
import flink.util.ExceptionUtils;

/**
 * 
 * ��Ͷ���ô���
 * 
 */
public class AipFeeCode extends AbstractType {
	public static Map<String, AipFeeCode> ALL = new LinkedHashMap<String, AipFeeCode>();

	public static final AipFeeCode FEECODE_1 = new AipFeeCode("����������", "1");
	public static final AipFeeCode FEECODE_2 = new AipFeeCode("���������", "2");
	public static final AipFeeCode FEECODE_3 = new AipFeeCode("������", "3");
	public static final AipFeeCode FEECODE_4 = new AipFeeCode("�˱���", "4");
	public static final AipFeeCode FEECODE_5 = new AipFeeCode("�˻������", "5");

	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("aipFeeCodes", AipFeeCode.ALL.values());
	}

	public static AipFeeCode valueOf(String value) throws Exception {

		AipFeeCode type = ALL.get(value);
		if (null == type) {
			ExceptionUtils.logException(AipFeeCode.class, "���ʹ���");
		}
		return type;
	}

	protected AipFeeCode(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
}
