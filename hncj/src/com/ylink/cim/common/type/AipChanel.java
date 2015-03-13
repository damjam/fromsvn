package com.ylink.cim.common.type;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractType;
import flink.util.ExceptionUtils;

public class AipChanel extends AbstractType {
	public static Map<String, AipChanel> ALL = new LinkedHashMap<String, AipChanel>();

	public static final AipChanel BANK_COUNTER = new AipChanel("���й���", "1");
	public static final AipChanel PHONE = new AipChanel("�绰", "2");
	public static final AipChanel ONLINE_BANKING = new AipChanel("����", "3");
	public static final AipChanel SELF_SERVICETERMINAL = new AipChanel("����", "4");

	protected AipChanel(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static AipChanel valueOf(String value) throws Exception {

		AipChanel type = ALL.get(value);
		if (null == type) {
			ExceptionUtils.logException(AipChanel.class, "���ʹ���");
		}
		return type;
	}

	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("aipChanels", AipChanel.ALL.values());
	}
}
