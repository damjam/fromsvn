package com.ylink.cim.common.msg.handle;

import org.apache.commons.beanutils.BeanUtils;


public class MsgA2032 {
	/**
	 * 当前报文域长度与MsgField类中定义不一致时，可在此处重新定义
	 */
	
	public static MsgField memo = new MsgField();
	public static MsgField aip_type = new MsgField();
	public static MsgField aip_mode = new MsgField();
	public static MsgField plan_stat = new MsgField();
	static{
		try {
			BeanUtils.copyProperties(memo, MsgField.memo);
			memo.setLength(30);
			BeanUtils.copyProperties(aip_type, MsgField.aip_type);
			aip_type.setLength(31);
			BeanUtils.copyProperties(aip_mode, MsgField.aip_mode);
			aip_mode.setLength(20);
			BeanUtils.copyProperties(plan_stat, MsgField.plan_stat);
			plan_stat.setLength(20);
		} catch (Exception e) {
		}
		
	}

	public static MsgField[] A2032REQ = {
		MsgField.aip_no,
		MsgField.order_no,
		MsgField.aip_type,
		MsgField.plan_stat,
		MsgField.start_date,
		MsgField.end_date
		};
	
	public static MsgField[] A2032RES = {
		MsgField.order_no,
		MsgField.aip_no,
		MsgField.account_no,
		MsgField.cust_name,
		aip_type,
		aip_mode,
		MsgField.aip_amount,
		MsgField.aip_periods,
		MsgField.chn_aip_date,
		MsgField.end_date,
		plan_stat,
		memo,
		MsgField.e_term_type,
		MsgField.e_branch_id,
		MsgField.e_teller_id,
		MsgField.e_exch_time,
		MsgField.u_term_type,
		MsgField.u_teller_id,
		MsgField.u_oper,
		MsgField.u_time
		};
	

}
