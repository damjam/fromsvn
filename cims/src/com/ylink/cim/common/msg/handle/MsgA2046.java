package com.ylink.cim.common.msg.handle;

import org.apache.commons.beanutils.BeanUtils;

public class MsgA2046 extends MsgField {
	/**
	 * 当前报文域长度与MsgField类中定义不一致时，可在此处重新定义
	 */

	public static MsgField branch_id = new MsgField();
	static {
		try {
			BeanUtils.copyProperties(branch_id, MsgField.branch_id);
			branch_id.setLength(12);
		} catch (Exception e) {
		}

	}
	public static MsgField[] A2046REQ = { branch_id, MsgField.take_month

	};

	public static MsgField[] A2046RES = { MsgField.take_date };

}
