package com.ylink.cim.common.type;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractType;
import flink.util.ExceptionUtils;

public class PayChnlType extends AbstractType {

	public static Map<String, PayChnlType> ALL = new LinkedHashMap<String, PayChnlType>();

	public static final PayChnlType BANK_102 = new PayChnlType("中国工商银行", "102");
	public static final PayChnlType BANK_103 = new PayChnlType("中国农业银行", "103");
	public static final PayChnlType BANK_104 = new PayChnlType("中国银行", "104");
	public static final PayChnlType BANK_105 = new PayChnlType("中国建设银行", "105");
	public static final PayChnlType BANK_301 = new PayChnlType("交通银行", "301");
	public static final PayChnlType BANK_302 = new PayChnlType("中信银行", "302");
	public static final PayChnlType BANK_303 = new PayChnlType("中国光大银行", "303");
	public static final PayChnlType BANK_304 = new PayChnlType("华夏银行", "303");
	public static final PayChnlType BANK_305 = new PayChnlType("中国民生银行", "303");
	public static final PayChnlType BANK_306 = new PayChnlType("广东发展银行", "303");
	public static final PayChnlType BANK_307 = new PayChnlType("深圳发展银行", "307");
	public static final PayChnlType BANK_308 = new PayChnlType("招商银行", "308");
	public static final PayChnlType BANK_309 = new PayChnlType("兴业银行", "309");
	public static final PayChnlType BANK_001 = new PayChnlType("东莞银行", "001");
	public static final PayChnlType BANK_002 = new PayChnlType("广州银行", "002");
	
	protected PayChnlType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static PayChnlType valueOf(String value) throws Exception {

		PayChnlType type = ALL.get(value);
		if (null == type) {
			ExceptionUtils.logException(PayChnlType.class, "类型错误");
		}

		return type;
	}
	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("payChnlTypes", PayChnlType.ALL.values());
	}
}
