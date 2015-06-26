package com.ylink.cim.common.msg.test;

import java.util.Map;

import com.ylink.cim.common.msg.handle.HeadReqUtils;
import com.ylink.cim.common.msg.handle.MsgA1014;
import com.ylink.cim.common.msg.handle.MsgField;
import com.ylink.cim.common.msg.handle.MsgUtil;
import com.ylink.cim.common.util.SocketUtil;

public class TestMsgA1014 {

	public static void main(String[] args) throws Exception {
		testA1014();
	}

	private static void testA1014() throws Exception {
		Map map = HeadReqUtils.encapsulationHeadReqParam();

		map.put(MsgField.h_exch_code.getFieldCode(), "A1014");
		map.put(MsgField.aip_no.getFieldCode(), "P089958071");
		map.put(MsgField.aip_mode.getFieldCode(), "2");
		map.put(MsgField.aip_type.getFieldCode(), "Au");
		map.put(MsgField.aip_amount.getFieldCode(), "10000");
		map.put(MsgField.bs_flag.getFieldCode(), "s");
		String msg = MsgUtil.buildReqMsg(map, MsgA1014.A1014REQ);
		String ip = "168.33.120.25";
		int port = 13000;
		String resMsg = SocketUtil.sendRecInPool(ip);
		// String resMsg = ConnectUtil.sendRecv(msg, 3, "GBK");
		Map<String, MsgField> headMap = MsgUtil.parseResHead(resMsg);
		Map<String, MsgField> bodyMap = MsgUtil.parseResBody(resMsg, MsgA1014.A1014RES);
	}

}
