package com.ylink.cim.common.state;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import flink.util.AbstractState;
import flink.util.ExceptionUtils;

public class BillState extends AbstractState {
	public static Map<String, BillState> ALL = new LinkedHashMap<String, BillState>();

	public static final BillState UNPAY = new BillState("待缴", "00");
	public static final BillState PAID = new BillState("已缴", "01");
	public static final BillState REFUND = new BillState("已退款", "02");//所有账单在清算前可做退款处理
	public static final BillState PART_PAID = new BillState("部分已缴", "03");//
	public static final BillState REVERSE = new BillState("已冲正", "04");//
	public static final BillState SETTLED = new BillState("已清算", "05");//清算后的账单，除押金条外其余不可退款，如需退款，在冲正交易里操作
	public static final BillState VERIFIED = new BillState("已核销", "09");//核销后的账单除删除外不可做任何操作
	public static void setInReq(HttpServletRequest request) {
		request.setAttribute("billStates", BillState.ALL.values());
	}

	public static BillState valueOf(String value) throws Exception {

		BillState state = ALL.get(value);
		if (null == state) {
			ExceptionUtils.logException(BillState.class, "状态错误");
		}
		return state;
	}

	protected BillState(String name, String value) {
		super(name, value);
		ALL.put(value, this);
	}
}
