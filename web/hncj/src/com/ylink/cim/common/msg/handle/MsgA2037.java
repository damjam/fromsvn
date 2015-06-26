package com.ylink.cim.common.msg.handle;

import org.apache.commons.beanutils.BeanUtils;

public class MsgA2037 {
	/**
	 * 当前报文域长度与MsgField类中定义不一致时，可在此处重新定义
	 */

	public static MsgField serial_no = new MsgField();
	public static MsgField access_way = new MsgField();
	static {
		try {
			BeanUtils.copyProperties(serial_no, MsgField.serial_no);
			serial_no.setLength(18);
			BeanUtils.copyProperties(access_way, MsgField.access_way);
			access_way.setLength(10);
		} catch (Exception e) {
		}

	}

	public static MsgField[] A2037REQ = { MsgField.aip_no, MsgField.access_way, MsgField.start_app_date,
			MsgField.end_app_date };

	public static MsgField[] A2037RES = { MsgField.exch_date, serial_no, MsgField.aip_no, MsgField.account_no,
			MsgField.cust_name, MsgField.trade_type, MsgField.entr_flow_serial_no, access_way, MsgField.out_account_no,
			MsgField.in_account_no, MsgField.exch_bal, MsgField.send_stat, MsgField.in_account_flag, MsgField.remark,
			MsgField.bk_plat_date, MsgField.bk_seq_no, MsgField.bk_rsp_code, MsgField.bk_rsp_msg,
			MsgField.bk_check_stat };

}
