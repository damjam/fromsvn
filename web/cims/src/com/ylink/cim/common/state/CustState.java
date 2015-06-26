package com.ylink.cim.common.state;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractState;
import flink.util.ExceptionUtils;

public class CustState extends AbstractState {

	public static Map<String, CustState> ALL = new LinkedHashMap<String, CustState>();
	protected CustState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
	
	public static final CustState NORMAL = new CustState("Õý³£", "0");
	public static final CustState CANCEL = new CustState("×¢Ïú", "1");
	public static CustState valueOf(String value) throws Exception {
		CustState state = ALL.get(value);
		if(null == state){
			ExceptionUtils.logException(CustState.class, "×´Ì¬´íÎó");
		}
		return state;
	}
	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("custStates", CustState.ALL.values());
	}
}
