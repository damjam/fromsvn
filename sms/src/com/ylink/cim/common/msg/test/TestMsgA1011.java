package com.ylink.cim.common.msg.test;

import java.util.Map;

import com.ylink.cim.common.msg.handle.HeadReqUtils;
import com.ylink.cim.common.msg.handle.MsgA1011;
import com.ylink.cim.common.msg.handle.MsgField;
import com.ylink.cim.common.msg.handle.MsgUtil;
import com.ylink.cim.common.util.SocketUtil;

public class TestMsgA1011 {

	public static void main(String[] args) throws Exception {
		// testBuild();
		// test1();
		// testParse();
		// testA1011();
		testA1011();
	}

	private static void testA1011() throws Exception {
		Map map = HeadReqUtils.encapsulationHeadReqParam();

		map.put(MsgField.h_exch_code.getFieldCode(), "A1011");
		map.put(MsgField.aip_no.getFieldCode(), "P089958071");
		map.put(MsgField.aip_mode.getFieldCode(), "2");
		map.put(MsgField.aip_type.getFieldCode(), "Au");
		map.put(MsgField.aip_amount.getFieldCode(), "10000");
		map.put(MsgField.aip_period.getFieldCode(), "1");
		map.put(MsgField.chn_aip_date.getFieldCode(), "01,02,03,04,11,24");
		map.put(MsgField.is_delay.getFieldCode(), "1");
		map.put(MsgField.is_effect.getFieldCode(), "1");
		String msg = MsgUtil.buildReqMsg(map, MsgA1011.A1011REQ);
		String ip = "168.33.120.25";
		int port = 13000;
		String resMsg = SocketUtil.sendRecInPool(ip);
		// String resMsg = ConnectUtil.sendRecv(msg, 3, "GBK");
		Map<String, MsgField> headMap = MsgUtil.parseResHead(resMsg);
		Map<String, MsgField> bodyMap = MsgUtil.parseResBody(resMsg,
				MsgA1011.A1011RES);
	}

}
