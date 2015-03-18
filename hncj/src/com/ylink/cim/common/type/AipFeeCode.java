package com.ylink.cim.common.type;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractType;
import flink.util.ExceptionUtils;

/**
 * 
 * 定投费用代码
 * 
 */
public class AipFeeCode extends AbstractType {
	public static Map<String, AipFeeCode> ALL = new LinkedHashMap<String, AipFeeCode>();

	public static final AipFeeCode FEECODE_1 = new AipFeeCode("交易手续费", "1");
	public static final AipFeeCode FEECODE_2 = new AipFeeCode("提货手续费", "2");
	public static final AipFeeCode FEECODE_3 = new AipFeeCode("提货溢短", "3");
	public static final AipFeeCode FEECODE_4 = new AipFeeCode("运保费", "4");
	public static final AipFeeCode FEECODE_5 = new AipFeeCode("账户管理费", "5");

	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("aipFeeCodes", AipFeeCode.ALL.values());
	}

	public static AipFeeCode valueOf(String value) throws Exception {

		AipFeeCode type = ALL.get(value);
		if (null == type) {
			ExceptionUtils.logException(AipFeeCode.class, "类型错误");
		}
		return type;
	}

	protected AipFeeCode(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
}
