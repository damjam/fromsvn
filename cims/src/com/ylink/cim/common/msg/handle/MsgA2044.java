package com.ylink.cim.common.msg.handle;

import org.apache.commons.beanutils.BeanUtils;

public class MsgA2044 extends MsgField {

	public static MsgField curr_can_use = new MsgField();
	static {
		try {
			BeanUtils.copyProperties(curr_can_use, MsgField.curr_can_use);
			curr_can_use.setLength(18);
			curr_can_use.setDeciLength(4);
		} catch (Exception e) {
		}

	}

	public static MsgField[] A2044REQ = { MsgField.aip_no

	};

	public static MsgField[] A2044RES = { MsgField.aip_no, MsgField.curr_amt, curr_can_use, MsgField.sell_froz_amt,
			MsgField.trade_app_froz };

}
