package com.ylink.cim.common.msg.handle;

import org.apache.commons.beanutils.BeanUtils;


public class MsgA2039 {
	/**
	 * ��ǰ�����򳤶���MsgField���ж��岻һ��ʱ�����ڴ˴����¶���
	 */
	
	public static MsgField aip_type = new MsgField();
	public static MsgField branch_id = new MsgField();
	public static MsgField addr = new MsgField();
	public static MsgField tel = new MsgField();
	static{
		try {
			BeanUtils.copyProperties(aip_type, MsgField.aip_type);
			aip_type.setLength(20);
			BeanUtils.copyProperties(branch_id, MsgField.branch_id);
			branch_id.setLength(50);
			BeanUtils.copyProperties(addr, MsgField.addr);
			addr.setLength(200);
			BeanUtils.copyProperties(tel, MsgField.tel);
			tel.setLength(30);
		} catch (Exception e) {
		}
		
	}

	public static MsgField[] A2039REQ = {
		MsgField.aip_no
		};
	
	public static MsgField[] A2039RES = {
		MsgField.curr_plan_weight,
		MsgField.curr_buy_weight,
		MsgField.curr_sell_weight,
		MsgField.curr_take_weight
		};
	

}
