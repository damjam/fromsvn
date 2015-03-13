package com.ylink.cim.common.msg.handle;

import org.apache.commons.beanutils.BeanUtils;


public class MsgA2031 {
	/**
	 * 当前报文域长度与MsgField类中定义不一致时，可在此处重新定义
	 */
	
	public static MsgField branch_id = new MsgField();
	public static MsgField broker_id = new MsgField();
	public static MsgField grade_id = new MsgField();
	public static MsgField area_code = new MsgField();
	public static MsgField memo = new MsgField();
	public static MsgField cert_type = new MsgField();
	public static MsgField o_date = new MsgField();
	static{
		try {
			BeanUtils.copyProperties(branch_id, MsgField.branch_id);
			branch_id.setLength(50);
			BeanUtils.copyProperties(broker_id, MsgField.broker_id);
			broker_id.setLength(30);
			BeanUtils.copyProperties(grade_id, MsgField.grade_id);
			grade_id.setLength(20);
			BeanUtils.copyProperties(area_code, MsgField.area_code);
			area_code.setLength(20);
			BeanUtils.copyProperties(memo, MsgField.memo);
			memo.setLength(255);
			BeanUtils.copyProperties(cert_type, MsgField.cert_type);
			cert_type.setLength(20);
			BeanUtils.copyProperties(o_date, MsgField.o_date);
			o_date.setLength(20);
		} catch (Exception e) {
		}
		
	}

	public static MsgField[] A2031REQ = {
		MsgField.aip_no
//		,
//		MsgField.cert_type,
//		MsgField.cert_num
		};
	
	public static MsgField[] A2031RES = {
		MsgField.aip_no,
		MsgField.account_no,
		MsgField.is_sign,
		MsgField.cust_name,
		cert_type,
		MsgField.cert_num,
		MsgField.acct_stat,
		branch_id,
		broker_id,
		grade_id,
		area_code,
		MsgField.mobile_phone,
		MsgField.tel,
		MsgField.fax,
		MsgField.addr,
		MsgField.zipcode,
		MsgField.email,
		memo,
		MsgField.o_term_type,
		MsgField.o_teller_id,
		o_date,
		MsgField.m_term_type,
		MsgField.m_teller_id,
		MsgField.m_date
		};
	

}
