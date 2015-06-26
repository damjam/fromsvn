package com.ylink.cim.common.msg.handle;


public class MsgA1014 {
	
	public static MsgField[] A1014REQ = {
		MsgField.aip_no,
		MsgField.bs_flag,
		MsgField.aip_type,
		MsgField.aip_mode,
		MsgField.aip_amount
		};
	
	public static MsgField[] A1014RES = {
		MsgField.order_no
		};
	

}
