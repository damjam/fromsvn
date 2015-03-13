package com.ylink.cim.common.msg.handle;

import org.apache.commons.beanutils.BeanUtils;


public class MsgA2033 {
	/**
	 * 当前报文域长度与MsgField类中定义不一致时，可在此处重新定义
	 */
	
	public static MsgField memo = new MsgField();
	public static MsgField aip_type = new MsgField();
	public static MsgField bs_flag = new MsgField();
	public static MsgField aip_mode = new MsgField();
	static{
		try {
			BeanUtils.copyProperties(memo, MsgField.memo);
			memo.setLength(100);
			BeanUtils.copyProperties(aip_type, MsgField.aip_type);
			aip_type.setLength(31);
			BeanUtils.copyProperties(bs_flag, MsgField.bs_flag);
			bs_flag.setLength(31);
			BeanUtils.copyProperties(aip_mode, MsgField.aip_mode);
			aip_mode.setLength(31);
		} catch (Exception e) {
		}
		
	}

	public static MsgField[] A2033REQ = {
		MsgField.aip_no,
		MsgField.start_date,
		MsgField.end_date
		};
	
	public static MsgField[] A2033RES = {
		MsgField.exch_time,
		MsgField.change_type,
		MsgField.exch_weight
		};
	

}
