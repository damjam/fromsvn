package com.ylink.cim.common.msg.handle;

import org.apache.commons.beanutils.BeanUtils;

public class MsgA2038 {
	/**
	 * 当前报文域长度与MsgField类中定义不一致时，可在此处重新定义
	 */

	public static MsgField variety_id = new MsgField();
	public static MsgField variety_name = new MsgField();
	public static MsgField branch_id = new MsgField();
	public static MsgField addr = new MsgField();
	public static MsgField tel = new MsgField();
	static {
		try {
			BeanUtils.copyProperties(variety_id, MsgField.variety_id);
			variety_id.setLength(3);
			BeanUtils.copyProperties(variety_name, MsgField.variety_name);
			variety_name.setLength(20);
			BeanUtils.copyProperties(branch_id, MsgField.branch_id);
			branch_id.setLength(50);
			BeanUtils.copyProperties(addr, MsgField.addr);
			addr.setLength(200);
			BeanUtils.copyProperties(tel, MsgField.tel);
			tel.setLength(30);
		} catch (Exception e) {
		}

	}

	public static MsgField[] A2038REQ = { MsgField.aip_no, MsgField.local_serial_no, MsgField.start_app_date,
			MsgField.end_app_date };

	public static MsgField[] A2038RES = { MsgField.aip_no, MsgField.account_no, MsgField.cust_name,
			MsgField.stat,
			MsgField.local_serial_no,
			variety_id,
			variety_name,
			MsgField.take_weight,
			MsgField.take_end_date,
			// MsgField.city_code,
			// MsgField.rsp_msg,
			// MsgField.sheet_no,
			MsgField.city_code, branch_id, MsgField.stor_id, MsgField.contacter, addr, MsgField.exch_date,
			MsgField.b_contacter, MsgField.b_addr, tel, MsgField.take_margin, MsgField.take_fare, MsgField.re_margin,
			MsgField.diff_bal, MsgField.process_cost

	};

}
