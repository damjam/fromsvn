package com.ylink.cim.common.msg.handle;

import org.apache.commons.beanutils.BeanUtils;

public class MsgA2046 extends MsgField {
	/**
	 * ��ǰ�����򳤶���MsgField���ж��岻һ��ʱ�����ڴ˴����¶���
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
