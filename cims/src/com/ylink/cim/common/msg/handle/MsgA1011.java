package com.ylink.cim.common.msg.handle;


public class MsgA1011 {
	
	public static MsgField[] A1011REQ = {
		MsgField.aip_no,
		MsgField.aip_mode,
		MsgField.aip_type,
		MsgField.aip_amount,
		MsgField.aip_period,
		MsgField.chn_aip_date,
		MsgField.is_delay,
		MsgField.is_effect
		};
	
	public static MsgField[] A1011RES = {
		MsgField.order_no
		};
	

}
