package com.ylink.cim.common.msg.handle;

import org.apache.commons.beanutils.BeanUtils;


public class MsgA0016 {
	/**
	 * ��ǰ�����򳤶���MsgField���ж��岻һ��ʱ�����ڴ˴����¶���
	 */
	
	public static MsgField branch_id = new MsgField();
	static{
		try {
			BeanUtils.copyProperties(branch_id, MsgField.branch_id);
			branch_id.setLength(10);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}
	public static MsgField[] A0016REQ = {
		branch_id
		};
	
	public static MsgField[] A0016RES = {
		MsgField.grade_id,
		MsgField.grade_name
		};
	

}
