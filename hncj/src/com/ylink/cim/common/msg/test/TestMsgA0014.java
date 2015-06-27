package com.ylink.cim.common.msg.test;

import java.util.Map;

import com.ylink.cim.common.msg.handle.HeadReqUtils;
import com.ylink.cim.common.msg.handle.MsgA0014;
import com.ylink.cim.common.msg.handle.MsgField;
import com.ylink.cim.common.msg.handle.MsgUtil;
import com.ylink.cim.common.util.SocketUtil;

public class TestMsgA0014 {

	public static void main(String[] args) throws Exception {
		// testBuild();
		// test1();
		// testParse();
		// testA0014();
		testA0014();
	}

	private static void testA0014() throws Exception {
		Map map = HeadReqUtils.encapsulationHeadReqParam();

		map.put(MsgField.h_exch_code.getFieldCode(), "A0014");
		map.put(MsgField.old_account_no.getFieldCode(), "1111111");
		map.put(MsgField.new_account_no.getFieldCode(), "2312312");
		map.put(MsgField.aip_no.getFieldCode(), "P089958069");
		String msg = MsgUtil.buildReqMsg(map, MsgA0014.A0014REQ);
		String ip = "168.33.120.25";
		int port = 13000;
		String resMsg = SocketUtil.sendRecInPool(ip);
		// String resMsg = ConnectUtil.sendRecv(msg, 3, "GBK");
		Map<String, MsgField> headMap = MsgUtil.parseResHead(resMsg);
		Map<String, MsgField> bodyMap = MsgUtil.parseResBody(resMsg,
				MsgA0014.A0014RES);
	}

}
