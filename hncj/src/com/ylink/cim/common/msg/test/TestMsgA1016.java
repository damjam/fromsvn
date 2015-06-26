package com.ylink.cim.common.msg.test;

import java.util.Map;

import com.ylink.cim.common.msg.handle.HeadReqUtils;
import com.ylink.cim.common.msg.handle.MsgA1016;
import com.ylink.cim.common.msg.handle.MsgField;
import com.ylink.cim.common.msg.handle.MsgUtil;
import com.ylink.cim.common.util.SocketUtil;

public class TestMsgA1016 {

	public static void main(String[] args) throws Exception {
		testA1016();
	}

	private static void testA1016() throws Exception {
		Map map = HeadReqUtils.encapsulationHeadReqParam();

		map.put(MsgField.h_exch_code.getFieldCode(), "A1016");
		map.put(MsgField.aip_no.getFieldCode(), "P089958072");
		String msg = MsgUtil.buildReqMsg(map, MsgA1016.A1016REQ);
		String ip = "168.33.120.25";
		int port = 13000;
		String resMsg = SocketUtil.sendRecInPool(ip);
		// String resMsg = ConnectUtil.sendRecv(msg, 3, "GBK");
		Map<String, MsgField> headMap = MsgUtil.parseResHead(resMsg);
		Map<String, MsgField> bodyMap = MsgUtil.parseResBody(resMsg, MsgA1016.A1016RES);
	}

}
