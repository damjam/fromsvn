package com.ylink.cim.common.msg.handle;


public class MsgA0011 {
	/**
	 * 当前报文域长度与MsgField类中定义不一致时，可在此处重新定义
	 */
	
	
	public static MsgField[] A0011REQ = {MsgField.account_no, MsgField.aip_no};
	
	public static MsgField[] A0011RES = {MsgField.serial_no};
	

}
