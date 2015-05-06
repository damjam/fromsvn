package com.ylink.cim.common.msg.handle;

public class MsgA2054 extends MsgField {

	public static MsgField[] A2054REQ = { MsgField.prod_code, MsgField.start_date, MsgField.end_date };

	public static MsgField[] A2054RES = { MsgField.exch_date, MsgField.prod_code, MsgField.high_price,
			MsgField.low_price, MsgField.aip_settle_price, MsgField.last_close_price, MsgField.open_price,
			MsgField.new_price

	};

}
