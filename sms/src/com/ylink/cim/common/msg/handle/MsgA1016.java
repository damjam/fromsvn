package com.ylink.cim.common.msg.handle;

import org.apache.commons.beanutils.BeanUtils;

public class MsgA1016 {
	/**
	 * 当前报文域长度与MsgField类中定义不一致时，可在此处重新定义
	 */

	public static MsgField variety_id = new MsgField();
	public static MsgField city_code = new MsgField();
	public static MsgField branch_id = new MsgField();
	static {
		try {
			BeanUtils.copyProperties(variety_id, MsgField.variety_id);
			variety_id.setLength(3);
			BeanUtils.copyProperties(city_code, MsgField.city_code);
			city_code.setLength(4);
			BeanUtils.copyProperties(branch_id, MsgField.branch_id);
			branch_id.setLength(15);
		} catch (Exception e) {
		}

	}
	public static MsgField[] A1016REQ = { MsgField.aip_no,
			MsgField.plan_exch_date, variety_id, MsgField.take_weight,
			city_code, branch_id };

	public static MsgField[] A1016RES = { MsgField.local_serial_no };

}
