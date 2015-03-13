package com.ylink.cim.common.msg.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ylink.cim.common.msg.handle.HeadReqUtils;
import com.ylink.cim.common.msg.handle.MsgA0017;
import com.ylink.cim.common.msg.handle.MsgA0011;
import com.ylink.cim.common.msg.handle.MsgA0017;
import com.ylink.cim.common.msg.handle.MsgA2040;
import com.ylink.cim.common.msg.handle.MsgField;
import com.ylink.cim.common.msg.handle.MsgUtil;
import com.ylink.cim.common.util.SocketUtil;

public class TestMsgA0017 {

	public static void main(String[] args) throws Exception{
		//testBuild();
		//test1();
		//testParse();
//		testA0017();
		testA0017();
	}
	private static void testA0017() throws Exception{
		Map map=HeadReqUtils.encapsulationHeadReqParam();
		
		map.put(MsgField.h_exch_code.getFieldCode(), "A0017");
		map.put(MsgField.aip_no.getFieldCode(), "P089958069");
		String msg = MsgUtil.buildReqMsg(map, MsgA0017.A0017REQ);
		String ip = "168.33.120.25";
		int port = 13000;
		String resMsg = SocketUtil.sendRecInPool(ip);
		//String resMsg = ConnectUtil.sendRecv(msg, 3, "GBK");
		Map<String, MsgField> headMap = MsgUtil.parseResHead(resMsg);
		Map<String, MsgField> bodyMap = MsgUtil.parseResBody(resMsg, MsgA0017.A0017RES);
	}
	
	
	
}
