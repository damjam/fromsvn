package com.ylink.cim.common.msg.handle;




public class MsgA2052 extends MsgField{
	
	
	public static MsgField[] A2052REQ = {
		MsgField.start_date,
		MsgField.end_date,
		MsgField.aip_type
		
	};
	
	public static MsgField[] A2052RES = {
		MsgField.exch_date,
		MsgField.type_name,
		MsgField.settle_price,
		MsgField.au100_take_fare,
		MsgField.au9999_take_fare
		
		};
	

}
