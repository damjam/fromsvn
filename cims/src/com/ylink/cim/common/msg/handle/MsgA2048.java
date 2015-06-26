package com.ylink.cim.common.msg.handle;



public class MsgA2048 extends MsgField{
	public static MsgField[] A2048REQ = {
		MsgField.start_date,
		MsgField.end_date,
		MsgField.aip_no
		
	};
	
	public static MsgField[] A2048RES = {
		MsgField.exch_date,
		MsgField.aip_no,
		MsgField.exch_fare,
		MsgField.acct_manage_fee,
		MsgField.trans_fee,
		MsgField.take_fare,
		MsgField.punish_interest
		
		};
	

}
