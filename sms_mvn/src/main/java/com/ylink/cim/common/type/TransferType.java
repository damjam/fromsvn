package com.ylink.cim.common.type;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractType;
import flink.util.ExceptionUtils;

public class TransferType extends AbstractType {

	public static Map<String, TransferType> ALL = new LinkedHashMap<String, TransferType>();

	public static final TransferType LEAVE = new TransferType("�ݼ�", "00");
	public static final TransferType TRANSFER = new TransferType("��λ/���ŵ���", "01");
	public static final TransferType QUIT = new TransferType("��ְ", "02");
	protected TransferType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static TransferType valueOf(String value) throws Exception {

		TransferType sysDictType = ALL.get(value);
		if (null == sysDictType) {
			ExceptionUtils.logException(TransferType.class, "���ʹ���");
		}
		return sysDictType;
	}

	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("dictTypes", ALL.values());
	}

}
