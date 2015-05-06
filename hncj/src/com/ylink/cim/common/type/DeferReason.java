package com.ylink.cim.common.type;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractType;
import flink.util.ExceptionUtils;

/**
 * 
 * 顺延原因
 * 
 */
public class DeferReason extends AbstractType {
	public static Map<String, DeferReason> ALL = new LinkedHashMap<String, DeferReason>();

	public static final DeferReason LACK_OF_FUNDS = new DeferReason("客户资金不足", "1");
	public static final DeferReason NOT_SUCCESSFULLY_COMPLETED = new DeferReason("交易无法顺利完成", "2");
	public static final DeferReason OTHER = new DeferReason("其他", "3");

	protected DeferReason(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static DeferReason valueOf(String value) throws Exception {

		DeferReason type = ALL.get(value);
		if (null == type) {
			ExceptionUtils.logException(DeferReason.class, "类型错误");
		}
		return type;
	}

	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("deferReasons", DeferReason.ALL.values());
	}
}
