package com.ylink.cim.common.msg.test;

import java.util.List;
import java.util.Map;

import com.ylink.cim.common.msg.handle.HeadReqUtils;
import com.ylink.cim.common.msg.handle.MsgA2042;
import com.ylink.cim.common.msg.handle.MsgA2044;
import com.ylink.cim.common.msg.handle.MsgField;
import com.ylink.cim.common.msg.handle.MsgUtil;
import com.ylink.cim.common.util.SocketUtil;

public class TestMsgA2044 {

	public static void main(String[] args) throws Exception{
		testA2044();
	}
	private static void testA2044() throws Exception{
		Map map=HeadReqUtils.encapsulationHeadReqParam();
		
		map.put(MsgField.h_exch_code.getFieldCode(), "A2044");
		map.put(MsgField.aip_no.getFieldCode(), "P089958072");
		String msg = MsgUtil.buildReqMsg(map, MsgA2044.A2044REQ);
		String ip = "168.33.120.25";
		int port = 13000;
		String resMsg = SocketUtil.sendRecInPool(ip);
		//String resMsg = ConnectUtil.sendRecv(msg, 3, "GBK");
		Map<String, MsgField> headMap = MsgUtil.parseResHead(resMsg);
		List<Map<String, MsgField>> bodyMapList = MsgUtil.parseResBodyList(resMsg, MsgA2044.A2044RES);
	}
	
	
	
}
