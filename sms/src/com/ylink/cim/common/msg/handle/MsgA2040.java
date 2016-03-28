package com.ylink.cim.common.msg.handle;

public class MsgA2040 extends MsgField {

	public static MsgField[] A2040REQ = { MsgField.aip_type };

	public static MsgField[] A2040RES = { MsgField.variety_id,
			MsgField.variety_name, MsgField.min_pickup, MsgField.pickup_base };

}
