package com.ylink.cim.common.type;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractType;

public class IdCardType extends AbstractType {

	public static Map<String, IdCardType> ALL = new LinkedHashMap<String, IdCardType>();
	private String ccbCardType;
	public static final IdCardType CARD_0 = new IdCardType("身份证", "0", "01");
	public static final IdCardType CARD_2 = new IdCardType("护照", "2", "07");

	protected IdCardType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	protected IdCardType(String name, String value, String ccbCardType) {
		super(name, value);
		this.ccbCardType = ccbCardType;
		ALL.put(value, this);
	}
	public static IdCardType valueOf(String value) {

		IdCardType type = ALL.get(value);
		if (null == type) {
			type = CARD_0;
		}
		return type;
	}
	public String getCcbCardType(){
		return this.ccbCardType;
	}
	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("idCardTypes", IdCardType.ALL.values());
	}
}
