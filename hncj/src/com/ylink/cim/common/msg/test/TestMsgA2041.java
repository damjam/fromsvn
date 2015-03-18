package com.ylink.cim.common.msg.test;

import java.util.Map;

import com.ylink.cim.common.msg.handle.HeadReqUtils;
import com.ylink.cim.common.msg.handle.MsgA2041;
import com.ylink.cim.common.msg.handle.MsgField;
import com.ylink.cim.common.msg.handle.MsgUtil;
import com.ylink.cim.common.util.SocketUtil;

public class TestMsgA2041 {

	public static void main(String[] args) throws Exception {
		testA2041();
	}

	private static void testA2041() throws Exception {
		Map map = HeadReqUtils.encapsulationHeadReqParam();

		map.put(MsgField.h_exch_code.getFieldCode(), "A2041");
		// map.put(MsgField.aip_type.getFieldCode(), "P089958072");
		String msg = MsgUtil.buildReqMsg(map, MsgA2041.A2041REQ);
		String ip = "168.33.120.25";
		int port = 13000;
		String resMsg = SocketUtil.sendRecInPool(ip);
		// String resMsg = ConnectUtil.sendRecv(msg, 3, "GBK");
		Map<String, MsgField> headMap = MsgUtil.parseResHead(resMsg);
		Map<String, MsgField> bodyMap = MsgUtil.parseResBody(resMsg, MsgA2041.A2041RES);
	}

}
