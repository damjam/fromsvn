package com.ylink.cim.common.msg.test;

import java.util.Map;

import com.ylink.cim.common.msg.handle.HeadReqUtils;
import com.ylink.cim.common.msg.handle.MsgA3001;
import com.ylink.cim.common.msg.handle.MsgField;
import com.ylink.cim.common.msg.handle.MsgUtil;
import com.ylink.cim.common.util.SocketUtil;

public class TestMsgA3001 {

	public static void main(String[] args) throws Exception {
		// testBuild();
		// test1();
		// testParse();
		// testA3001();
		testA3001();
	}

	private static void testA3001() throws Exception {
		Map map = HeadReqUtils.encapsulationHeadReqParam();

		map.put(MsgField.h_exch_code.getFieldCode(), "A3001");
		map.put(MsgField.aip_no.getFieldCode(), "P089958071");
		map.put(MsgField.account_no.getFieldCode(), "2312312");
		map.put(MsgField.access_way.getFieldCode(), "2");
		map.put(MsgField.exch_bal.getFieldCode(), "10000");
		String msg = MsgUtil.buildReqMsg(map, MsgA3001.A3001REQ);
		String ip = "168.33.120.25";
		int port = 13000;
		String resMsg = SocketUtil.sendRecInPool(ip);
		// String resMsg = ConnectUtil.sendRecv(msg, 3, "GBK");
		Map<String, MsgField> headMap = MsgUtil.parseResHead(resMsg);
		Map<String, MsgField> bodyMap = MsgUtil.parseResBody(resMsg, MsgA3001.A3001RES);
	}

}
