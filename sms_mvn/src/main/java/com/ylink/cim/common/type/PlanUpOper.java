package com.ylink.cim.common.type;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractType;
import flink.util.ExceptionUtils;

/**
 * 
 * 计划更新操作
 * 
 */
public class PlanUpOper extends AbstractType {
	public static Map<String, PlanUpOper> ALL = new LinkedHashMap<String, PlanUpOper>();

	public static final PlanUpOper APPLY_TO_UNDO = new PlanUpOper("申请->撤销", "1");
	public static final PlanUpOper APPLY_TO_MODIFY = new PlanUpOper("申请->修改",
			"2");
	public static final PlanUpOper EFFECT_TO_MODIFY = new PlanUpOper("生效->修改",
			"3");
	public static final PlanUpOper EFFECT_TO_TERMINAL = new PlanUpOper(
			"生效->终止", "4");
	public static final PlanUpOper EFFECT_TO_PAUSE = new PlanUpOper("生效->暂停",
			"5");
	public static final PlanUpOper PAUSE_TO_RESTORE = new PlanUpOper("暂停->恢复",
			"6");
	public static final PlanUpOper PAUSE_TO_TERMINAL = new PlanUpOper("暂停->终止",
			"7");
	public static final PlanUpOper MORETHAN_FAILUREPHASES_TO_TERMINAL = new PlanUpOper(
			"超过扣款失败期数->终止", "8");

	protected PlanUpOper(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}

	public static PlanUpOper valueOf(String value) throws Exception {

		PlanUpOper type = ALL.get(value);
		if (null == type) {
			ExceptionUtils.logException(PlanUpOper.class, "类型错误");
		}
		return type;
	}

	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("planUpOpers", PlanUpOper.ALL.values());
	}
}
