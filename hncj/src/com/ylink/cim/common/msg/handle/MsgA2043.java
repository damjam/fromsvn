package com.ylink.cim.common.msg.handle;

import org.apache.commons.beanutils.BeanUtils;


public class MsgA2043 extends MsgField{
	/**
	 * 当前报文域长度与MsgField类中定义不一致时，可在此处重新定义
	 */
	
	public static MsgField city_code = new MsgField();
	public static MsgField branch_id = new MsgField();
	public static MsgField addr = new MsgField();
	public static MsgField tel = new MsgField();
	static{
		try {
			BeanUtils.copyProperties(city_code, MsgField.city_code);
			city_code.setLength(4);
			BeanUtils.copyProperties(branch_id, MsgField.branch_id);
			branch_id.setLength(12);
			BeanUtils.copyProperties(addr, MsgField.addr);
			addr.setLength(200);
			BeanUtils.copyProperties(tel, MsgField.tel);
			tel.setLength(30);
		} catch (Exception e) {
		}
		
	}
	public static MsgField[] A2043REQ = {
		city_code
		
	};
	
	public static MsgField[] A2043RES = {
		branch_id,
		MsgField.branch_name,
		MsgField.stor_id,
		MsgField.contacter,
		addr,
		MsgField.b_contacter,
		MsgField.b_addr,
		tel
		};
	

}
