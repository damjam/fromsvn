package com.ylink.cim.common.msg.handle;

import org.apache.commons.beanutils.BeanUtils;

public class MsgA3001 {

	public static MsgField deduct_type = new MsgField();
	static {
		try {
			BeanUtils.copyProperties(deduct_type, MsgField.deduct_type);
			deduct_type.setLength(2);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	public static MsgField[] A3001REQ = { MsgField.aip_no, MsgField.account_no,
			deduct_type, MsgField.exch_bal };

	public static MsgField[] A3001RES = { MsgField.order_no };

}
