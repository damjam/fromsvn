package com.ylink.cim.common.msg.test;

import java.util.List;
import java.util.Map;

import com.ylink.cim.common.msg.handle.HeadReqUtils;
import com.ylink.cim.common.msg.handle.MsgA2052;
import com.ylink.cim.common.msg.handle.MsgField;
import com.ylink.cim.common.msg.handle.MsgUtil;
import com.ylink.cim.common.util.SocketUtil;

public class TestMsgA2052 {

	public static void main(String[] args) throws Exception {
		testA2052();
	}

	private static void testA2052() throws Exception {
		Map map = HeadReqUtils.encapsulationHeadReqParam();

		map.put(MsgField.h_exch_code.getFieldCode(), "A2052");
		map.put(MsgField.aip_type.getFieldCode(), "Au");
		// map.put(MsgField.take_month.getFieldCode(), "201305");
		String msg = MsgUtil.buildReqMsg(map, MsgA2052.A2052REQ);
		String ip = "168.33.120.25";
		int port = 13000;
		String resMsg = SocketUtil.sendRecInPool(ip);
		// String resMsg = ConnectUtil.sendRecv(msg, 3, "GBK");
		Map<String, MsgField> headMap = MsgUtil.parseResHead(resMsg);
		List<Map<String, MsgField>> bodyMapList = MsgUtil.parseResBodyList(
				resMsg, MsgA2052.A2052RES);
	}

}
