package com.ylink.cim.common.msg.handle;

import org.apache.commons.beanutils.BeanUtils;

public class MsgA0010 {
	/**
	 * 当前报文域长度与MsgField类中定义不一致时，可在此处重新定义
	 */
	
	public static MsgField memo = new MsgField();
	static{
		try {
			BeanUtils.copyProperties(memo, MsgField.memo);
			memo.setLength(255);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	public static MsgField[] A0010REQ = {MsgField.acct_no, MsgField.cust_name, MsgField.cert_type, 
		MsgField.cert_num, MsgField.broker_id, MsgField.grade_id, MsgField.area_code, MsgField.mobile_phone,
		MsgField.tel, MsgField.fax, MsgField.addr, MsgField.zipcode, MsgField.email, memo};
	
	public static MsgField[] A0010RES = {MsgField.serial_no, MsgField.o_date, MsgField.aip_no};
	

}
