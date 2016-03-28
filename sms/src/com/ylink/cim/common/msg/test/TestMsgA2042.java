package com.ylink.cim.common.msg.test;

import java.util.List;
import java.util.Map;

import com.ylink.cim.common.msg.handle.HeadReqUtils;
import com.ylink.cim.common.msg.handle.MsgA2042;
import com.ylink.cim.common.msg.handle.MsgField;
import com.ylink.cim.common.msg.handle.MsgUtil;
import com.ylink.cim.common.util.SocketUtil;

public class TestMsgA2042 {

	public static void main(String[] args) throws Exception {
		testA2042();
	}

	private static void testA2042() throws Exception {
		Map map = HeadReqUtils.encapsulationHeadReqParam();

		map.put(MsgField.h_exch_code.getFieldCode(), "A2042");
		// map.put(MsgField.aip_type.getFieldCode(), "P089958072");
		String msg = MsgUtil.buildReqMsg(map, MsgA2042.A2042REQ);
		String ip = "168.33.120.25";
		int port = 13000;
		String resMsg = SocketUtil.sendRecInPool(ip);
		// String resMsg = ConnectUtil.sendRecv(msg, 3, "GBK");
		Map<String, MsgField> headMap = MsgUtil.parseResHead(resMsg);
		// Map<String, MsgField> bodyMap = MsgUtil.parseResBody(resMsg,
		// MsgA2042.A2042RES);
		List<Map<String, MsgField>> bodyMapList = MsgUtil.parseResBodyList(
				resMsg, MsgA2042.A2042RES);
	}

}
