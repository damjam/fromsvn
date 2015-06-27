package com.ylink.cim.common.msg.handle;

public class MsgA3002 {
	/**
	 * 当前报文域长度与MsgField类中定义不一致时，可在此处重新定义
	 */

	static {

	}

	public static MsgField[] A3002REQ = { MsgField.aip_no, };

	public static MsgField[] A3002RES = { MsgField.curr_bal,
			MsgField.curr_can_use, MsgField.frozen_fund, MsgField.debt_bal };

}
