package com.ylink.cim.common.state;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractState;
import flink.util.ExceptionUtils;

public class RepairState extends AbstractState {
	public static Map<String, RepairState> ALL = new LinkedHashMap<String, RepairState>();

	public static final RepairState UNDER_DEAL = new RepairState("待处理", "00");
	public static final RepairState DEALING = new RepairState("处理中", "01");
	public static final RepairState DEALED = new RepairState("已处理", "02");//所有账单在清算前可做退款处理
	public static final RepairState TRANSFERED = new RepairState("已转移", "03");//
	public static final RepairState CANCELED = new RepairState("已撤销", "04");//
	
	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("repairStates", RepairState.ALL.values());
	}

	public static RepairState valueOf(String value) throws Exception {

		RepairState state = ALL.get(value);
		if (null == state) {
			ExceptionUtils.logException(RepairState.class, "状态错误");
		}
		return state;
	}

	protected RepairState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
}
