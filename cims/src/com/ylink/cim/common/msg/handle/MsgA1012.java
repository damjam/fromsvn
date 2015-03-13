package com.ylink.cim.common.msg.handle;


public class MsgA1012 {
	
	public static MsgField[] A1012REQ = {
		MsgField.aip_no,
		MsgField.order_no,
		MsgField.aip_mode,
		MsgField.aip_amount,
		MsgField.chn_aip_date,
		MsgField.is_delay,
		MsgField.memo
		};
	
	public static MsgField[] A1012RES = {
		MsgField.order_no
		};
	

}
