package com.ylink.cim.common.msg.handle;

import org.apache.commons.beanutils.BeanUtils;


public class MsgA2042 extends MsgField{
	/**
	 * 当前报文域长度与MsgField类中定义不一致时，可在此处重新定义
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
