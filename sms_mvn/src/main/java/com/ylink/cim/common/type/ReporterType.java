package com.ylink.cim.common.type;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractType;
import flink.util.ExceptionUtils;

public class ReporterType extends AbstractType {

	public static Map<String, ReporterType> ALL = new LinkedHashMap<String, ReporterType>();

	public static final ReporterType OWNER = new ReporterType("ҵ��", "00");
	public static final ReporterType STAFF = new ReporterType("Ա��", "01");
	public static final ReporterType OTHER = new ReporterType("����", "02");
	protected ReporterType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static ReporterType valueOf(String value) throws Exception {

		ReporterType sysDictType = ALL.get(value);
		if (null == sysDictType) {
			ExceptionUtils.logException(ReporterType.class, "���ʹ���");
		}
		return sysDictType;
	}

	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("reporterTypes", ALL.values());
	}

}
