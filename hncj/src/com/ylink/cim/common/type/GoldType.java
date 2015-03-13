package com.ylink.cim.common.type;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractState;
import flink.util.ExceptionUtils;

/**
 * 
 * 黄金种类
 *
 */
public class GoldType extends AbstractState {
	public static Map<String, GoldType> ALL = new LinkedHashMap<String, GoldType>();
	protected GoldType(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static final GoldType Au99 = new GoldType("Au99.99", "0");
	public static final GoldType Au100 = new GoldType("Au100g", "1");
	public static final GoldType CUSTOMIZE = new GoldType("自主品牌", "2");
	public static GoldType valueOf(String value) throws Exception {
		GoldType flag = ALL.get(value);
		if(null == flag){
			ExceptionUtils.logException(GoldType.class, "标识错误");
		}
		return flag;
	}
	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("yesOrNos", GoldType.ALL.values());
	}
}
