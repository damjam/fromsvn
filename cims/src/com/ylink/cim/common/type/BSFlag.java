package com.ylink.cim.common.type;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractState;
import flink.util.ExceptionUtils;

/**
 * 
 * 买卖标识
 *
 */
public class BSFlag extends AbstractState {
	public static Map<String, BSFlag> ALL = new LinkedHashMap<String, BSFlag>();
	protected BSFlag(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static final BSFlag BUY = new BSFlag("买", "b");
	public static final BSFlag SELL = new BSFlag("卖", "s");
	public static BSFlag valueOf(String value) throws Exception {
		BSFlag flag = ALL.get(value);
		if(null == flag){
			ExceptionUtils.logException(BSFlag.class, "标识错误");
		}
		return flag;
	}
	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("bsFlags", BSFlag.ALL.values());
	}
}
