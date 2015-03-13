package com.ylink.cim.common.type;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.etc.Symbol;
import flink.util.AbstractType;
import flink.util.ExceptionUtils;

public class SymbolType extends AbstractType {

	public static Map<String, SymbolType> ALL = new LinkedHashMap<String, SymbolType>();

	public static final SymbolType YES = new SymbolType(" «", Symbol.YES);
	public static final SymbolType NO = new SymbolType("∑Ò", Symbol.NO);

	protected SymbolType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static SymbolType valueOf(String value) throws Exception {

		SymbolType type = ALL.get(value);
		if (null == type) {
			ExceptionUtils.logException(SymbolType.class, "¿‡–Õ¥ÌŒÛ");
		}
		return type;
	}

	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("symbolTypes", SymbolType.ALL.values());
	}
}
