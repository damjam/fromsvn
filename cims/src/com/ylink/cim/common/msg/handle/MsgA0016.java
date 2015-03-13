package com.ylink.cim.common.msg.handle;

import org.apache.commons.beanutils.BeanUtils;


public class MsgA0016 {
	/**
	 * 当前报文域长度与MsgField类中定义不一致时，可在此处重新定义
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
