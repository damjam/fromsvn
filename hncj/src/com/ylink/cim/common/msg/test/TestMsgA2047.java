package com.ylink.cim.common.msg.test;

import java.util.List;
import java.util.Map;

import com.ylink.cim.common.msg.handle.HeadReqUtils;
import com.ylink.cim.common.msg.handle.MsgA2047;
import com.ylink.cim.common.msg.handle.MsgField;
import com.ylink.cim.common.msg.handle.MsgUtil;
import com.ylink.cim.common.util.SocketUtil;

public class TestMsgA2047 {

	public static void main(String[] args) throws Exception{
		testA2047();
	}
	private static void testA2047() throws Exception{
		Map map=HeadReqUtils.encapsulationHeadReqParam();
		
		map.put(MsgField.h_exch_code.getFieldCode(), "A2047");
//		map.put(MsgField.branch_id.getFieldCode(), "0000136001");
//		map.put(MsgField.take_month.getFieldCode(), "201305");
		String msg = MsgUtil.buildReqMsg(map, MsgA2047.A2047REQ);
		String ip = "168.33.120.25";
		int port = 13000;
		String resMsg = SocketUtil.sendRecInPool(ip);
		//String resMsg = ConnectUtil.sendRecv(msg, 3, "GBK");
		Map<String, MsgField> headMap = MsgUtil.parseResHead(resMsg);
		List<Map<String, MsgField>> bodyMapList = MsgUtil.parseResBodyList(resMsg, MsgA2047.A2047RES);
	}
	
	
	
}
