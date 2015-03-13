package com.ylink.cim.common.msg.handle;



public class MsgA2047 extends MsgField{
	public static MsgField[] A2047REQ = {
		MsgField.start_date,
		MsgField.end_date,
		MsgField.prod_code,
		MsgField.aip_type
		
	};
	
	public static MsgField[] A2047RES = {
		MsgField.exch_date,
		MsgField.prod_type,
		MsgField.settle_price,
		MsgField.take_fare,
		MsgField.open_price,
		MsgField.max_price,
		MsgField.min_price,
		MsgField.close_price
		
		};
	

}
