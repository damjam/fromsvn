package com.ylink.cim.common.msg.test;

import java.util.Map;

import com.ylink.cim.common.msg.handle.HeadReqUtils;
import com.ylink.cim.common.msg.handle.MsgA0010;
import com.ylink.cim.common.msg.handle.MsgA0011;
import com.ylink.cim.common.msg.handle.MsgField;
import com.ylink.cim.common.msg.handle.MsgUtil;
import com.ylink.cim.common.util.SocketUtil;

public class TestMsg {

	public static void main(String[] args) throws Exception {
		// testBuild();
		// test1();
		// testParse();
		// testA0010();
		testA0011();
	}

	private static void testA0010() throws Exception {
		Map map = HeadReqUtils.encapsulationHeadReqParam();

		map.put(MsgField.acct_no.getFieldCode(), "71221234123413241");
		map.put(MsgField.h_exch_code.getFieldCode(), "A0010");
		map.put(MsgField.area_code.getFieldCode(), "110000");
		map.put(MsgField.cert_num.getFieldCode(), "4123432435234");
		map.put(MsgField.cert_type.getFieldCode(), "0");
		map.put(MsgField.cust_name.getFieldCode(), "≤‚ ‘");
		map.put(MsgField.mobile_phone.getFieldCode(), "13222223333");
		map.put(MsgField.tel.getFieldCode(), "82349845");
		map.put(MsgField.zipcode.getFieldCode(), "518000");

		map.put(MsgField.h_bank_no.getFieldCode(), "");
		map.put(MsgField.h_cashier_id.getFieldCode(), "1");
		map.put(MsgField.h_teller_id.getFieldCode(), "100");
		map.put(MsgField.h_teller_id1.getFieldCode(), "0011");
		map.put(MsgField.h_teller_id2.getFieldCode(), "0012");
		map.put(MsgField.h_channel.getFieldCode(), "2");
		map.put(MsgField.h_term_id.getFieldCode(), "0000");
		map.put(MsgField.h_rsp_type.getFieldCode(), "");
		map.put(MsgField.h_req_type.getFieldCode(), "T");
		map.put(MsgField.h_auth_lvl.getFieldCode(), "00");
		map.put(MsgField.h_start_num.getFieldCode(), "1");
		map.put(MsgField.h_query_num.getFieldCode(), "20");
		map.put(MsgField.h_exch_code.getFieldCode(), "A0010");
		map.put(MsgField.h_bk_serial_no.getFieldCode(), "");
		map.put(MsgField.h_proxy.getFieldCode(), "0");
		map.put(MsgField.h_branch_id.getFieldCode(), "0000136001");
		String msg = MsgUtil.buildReqMsg(map, MsgA0010.A0010REQ);
		/*
		 * int len = msg.getBytes().length; StringBuilder prefix =
		 * MsgUtil.fill(new StringBuilder(msg), '0',
		 * 4-String.valueOf(len).length(), 'L'); prefix.append(msg);
		 */
		// msg =
		// "                                        GGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGA0010                                      20130523     0000136001                                  2              1       100                                                                                     123            123                            s 123                                                                   110000    13222223333                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    ";
		String ip = "168.33.120.25";
		int port = 13000;
		/*
		 * Socket socket = SocketUtil.sendMsg(ip, port, msg, 5, "GBK"); String
		 * resMsg = SocketUtil.recvMsg(socket, 5, "GBK", true);
		 */
		// Socket socket2 = ConnectionProvider.getInstance().getConnection();
		// SocketUtil.sendMsg(socket2, msg, 3, "GBK");
		String resMsg = SocketUtil.sendRecInPool(ip);
		// String resMsg = ConnectUtil.sendRecv(msg, 3, "GBK");
		Map<String, MsgField> headMap = MsgUtil.parseResHead(resMsg);
		Map<String, MsgField> bodyMap = MsgUtil.parseResBody(resMsg, MsgA0010.A0010RES);
	}

	private static void testA0011() throws Exception {
		Map map = HeadReqUtils.encapsulationHeadReqParam();

		map.put(MsgField.h_exch_code.getFieldCode(), "A0011");
		map.put(MsgField.account_no.getFieldCode(), "2312312");
		map.put(MsgField.aip_no.getFieldCode(), "P089958072");
		String msg = MsgUtil.buildReqMsg(map, MsgA0011.A0011REQ);
		/*
		 * int len = msg.getBytes().length; StringBuilder prefix =
		 * MsgUtil.fill(new StringBuilder(msg), '0',
		 * 4-String.valueOf(len).length(), 'L'); prefix.append(msg);
		 */
		// msg =
		// "                                        GGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGA0010                                      20130523     0000136001                                  2              1       100                                                                                     123            123                            s 123                                                                   110000    13222223333                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    ";
		String ip = "168.33.120.25";
		int port = 13000;
		/*
		 * Socket socket = SocketUtil.sendMsg(ip, port, msg, 5, "GBK"); String
		 * resMsg = SocketUtil.recvMsg(socket, 5, "GBK", true);
		 */
		// Socket socket2 = ConnectionProvider.getInstance().getConnection();
		// SocketUtil.sendMsg(socket2, msg, 3, "GBK");
		String resMsg = SocketUtil.sendRecInPool(ip);
		// String resMsg = ConnectUtil.sendRecv(msg, 3, "GBK");
		Map<String, MsgField> headMap = MsgUtil.parseResHead(resMsg);
		Map<String, MsgField> bodyMap = MsgUtil.parseResBody(resMsg, MsgA0011.A0011RES);
	}

}
