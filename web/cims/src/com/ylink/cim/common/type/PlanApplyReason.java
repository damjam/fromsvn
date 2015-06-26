package com.ylink.cim.common.type;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractType;
import flink.util.ExceptionUtils;

/**
 * 
 * º∆ªÆ…Í«Î‘≠“Ú
 * 
 */
public class PlanApplyReason extends AbstractType {
	public static Map<String, PlanApplyReason> ALL = new LinkedHashMap<String, PlanApplyReason>();

	public static final PlanApplyReason APPLY_REASON_1 = new PlanApplyReason("–¬…Í«Î", "1");
	public static final PlanApplyReason APPLY_REASON_2 = new PlanApplyReason("…Í«Î–ﬁ∏ƒ->…Í«Î", "2");
	public static final PlanApplyReason APPLY_REASON_3 = new PlanApplyReason("…˙–ß–ﬁ∏ƒ->…Í«Î", "3");
	public static final PlanApplyReason APPLY_REASON_4 = new PlanApplyReason("…˙–ß‘›Õ£->…Í«Î£®‘›Õ££©", "4");
	public static final PlanApplyReason APPLY_REASON_5 = new PlanApplyReason("‘›Õ£ª÷∏¥->…Í«Î", "5");

	protected PlanApplyReason(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static PlanApplyReason valueOf(String value) throws Exception {

		PlanApplyReason type = ALL.get(value);
		if (null == type) {
			ExceptionUtils.logException(PlanApplyReason.class, "¿‡–Õ¥ÌŒÛ");
		}
		return type;
	}

	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("planApplyReasons", PlanApplyReason.ALL.values());
	}
}
