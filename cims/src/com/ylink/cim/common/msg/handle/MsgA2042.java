package com.ylink.cim.common.msg.handle;

import org.apache.commons.beanutils.BeanUtils;


public class MsgA2042 extends MsgField{
	/**
	 * ��ǰ�����򳤶���MsgField���ж��岻һ��ʱ�����ڴ˴����¶���
	 */
	
	public static MsgField city_code = new MsgField();
	static{
		try {
			BeanUtils.copyProperties(city_code, MsgField.city_code);
			city_code.setLength(4);
		} catch (Exception e) {
		}
		
	}
	public static MsgField[] A2042REQ = {};
	
	public static MsgField[] A2042RES = {
		city_code,
		MsgField.city_name
		};
	

}
