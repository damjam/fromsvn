package com.ylink.cim.common.type;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractState;
import flink.util.ExceptionUtils;

/**
 * 
 * ������ʶ
 *
 */
public class BSFlag extends AbstractState {
	public static Map<String, BSFlag> ALL = new LinkedHashMap<String, BSFlag>();
	public static final BSFlag BUY = new BSFlag("��", "b");
	
	public static final BSFlag SELL = new BSFlag("��", "s");
	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("bsFlags", BSFlag.ALL.values());
	}
	public static BSFlag valueOf(String value) throws Exception {
		BSFlag flag = ALL.get(value);
		if(null == flag){
			ExceptionUtils.logException(BSFlag.class, "��ʶ����");
		}
		return flag;
	}
	protected BSFlag(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
}
