package com.ylink.cim.common.msg.test;

import java.util.Map;

import com.ylink.cim.common.msg.GoldSorket;
import com.ylink.cim.common.msg.handle.HeadReqUtils;
import com.ylink.cim.common.msg.handle.MsgA2031;
import com.ylink.cim.common.msg.handle.MsgField;
import com.ylink.cim.common.msg.handle.MsgUtil;

public class TestMsgA2031 {

	public static void main(String[] args) throws Exception {
		testA2031();
	}

	private static void testA2031() throws Exception {
		Map map = HeadReqUtils.encapsulationHeadReqParam();

		map.put(MsgField.h_exch_code.getFieldCode(), "A2031");
		map.put(MsgField.aip_no.getFieldCode(), "P089958072");
		map.put(MsgField.cert_type.getFieldCode(), "0");
		map.put(MsgField.cert_num.getFieldCode(), "4123432435234");
		String msg = MsgUtil.buildReqMsg(map, MsgA2031.A2031REQ);
		String ip = "168.33.120.25";
		int port = 13000;
		String resMsg = GoldSorket.sendMessage(msg);
		// String resMsg = ConnectUtil.sendRecv(msg, 3, "GBK");
		Map<String, MsgField> headMap = MsgUtil.parseResHead(resMsg);
		Map<String, MsgField> bodyMap = MsgUtil.parseResBody(resMsg, MsgA2031.A2031RES);
	}

}
