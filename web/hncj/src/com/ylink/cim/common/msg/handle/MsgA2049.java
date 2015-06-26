package com.ylink.cim.common.msg.handle;

import org.apache.commons.beanutils.BeanUtils;



public class MsgA2049 extends MsgField{
	
	public static MsgField exch_dire = new MsgField();
	public static MsgField in_account_flag = new MsgField();
	static{
		try {
			BeanUtils.copyProperties(exch_dire, MsgField.exch_dire);
			exch_dire.setLength(2);
			BeanUtils.copyProperties(in_account_flag, MsgField.in_account_flag);
			in_account_flag.setLength(2);
		} catch (Exception e) {
		}
		
	}
	
	public static MsgField[] A2049REQ = {
		MsgField.start_date,
		MsgField.end_date,
		exch_dire,
		MsgField.aip_no
		
	};
	
	public static MsgField[] A2049RES = {
		MsgField.exch_time,
		MsgField.aip_no,
		MsgField.deduct_type,
		MsgField.exch_bal,
		in_account_flag
		};
	

}
