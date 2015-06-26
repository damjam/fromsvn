package com.ylink.cim.common.msg.test;

import java.util.Map;

import com.ylink.cim.common.msg.handle.HeadReqUtils;
import com.ylink.cim.common.msg.handle.MsgA0016;
import com.ylink.cim.common.msg.handle.MsgField;
import com.ylink.cim.common.msg.handle.MsgUtil;
import com.ylink.cim.common.util.SocketUtil;

public class TestMsgA0016 {

	public static void main(String[] args) throws Exception{
		//testBuild();
		//test1();
		//testParse();
//		testA0016();
		testA0016();
	}
	private static void testA0016() throws Exception{
		Map map=HeadReqUtils.encapsulationHeadReqParam();
		
		map.put(MsgField.h_exch_code.getFieldCode(), "A0016");
		map.put(MsgField.branch_id.getFieldCode(), "0000136001");
		String msg = MsgUtil.buildReqMsg(map, MsgA0016.A0016REQ);
		String ip = "168.33.120.25";
		int port = 13000;
		String resMsg = SocketUtil.sendRecInPool(ip);
		//String resMsg = ConnectUtil.sendRecv(msg, 3, "GBK");
		Map<String, MsgField> headMap = MsgUtil.parseResHead(resMsg);
		Map<String, MsgField> bodyMap = MsgUtil.parseResBody(resMsg, MsgA0016.A0016RES);
	}
	
	
	
}
