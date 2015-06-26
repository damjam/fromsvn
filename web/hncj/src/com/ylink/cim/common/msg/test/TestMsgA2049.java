package com.ylink.cim.common.msg.test;

import java.util.List;
import java.util.Map;

import com.ylink.cim.common.msg.handle.HeadReqUtils;
import com.ylink.cim.common.msg.handle.MsgA2049;
import com.ylink.cim.common.msg.handle.MsgField;
import com.ylink.cim.common.msg.handle.MsgUtil;
import com.ylink.cim.common.util.SocketUtil;

public class TestMsgA2049 {

	public static void main(String[] args) throws Exception{
		testA2049();
	}
	private static void testA2049() throws Exception{
		Map map=HeadReqUtils.encapsulationHeadReqParam();
		
		map.put(MsgField.h_exch_code.getFieldCode(), "A2049");
		map.put(MsgField.aip_no.getFieldCode(), "P089958072");
//		map.put(MsgField.take_month.getFieldCode(), "201305");
		String msg = MsgUtil.buildReqMsg(map, MsgA2049.A2049REQ);
		String ip = "168.33.120.25";
		int port = 13000;
		String resMsg = SocketUtil.sendRecInPool(ip);
		//String resMsg = ConnectUtil.sendRecv(msg, 3, "GBK");
		Map<String, MsgField> headMap = MsgUtil.parseResHead(resMsg);
		List<Map<String, MsgField>> bodyMapList = MsgUtil.parseResBodyList(resMsg, MsgA2049.A2049RES);
	}
	
	
	
}
