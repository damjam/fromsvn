package com.ylink.cim.common.msg.handle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ylink.cim.common.util.SocketUtil;

public class TestMsg {

	public static void main(String[] args) throws Exception {
		// testBuild();
		// test1();
		// testParse();
		testA0010();
		// testA0011();
	}

	private static void testA0010() throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put(MsgField.account_no.getFieldCode(), "71221234123413241");
		map.put(MsgField.h_exch_code.getFieldCode(), "A0010");
		map.put(MsgField.area_code.getFieldCode(), "110000");
		map.put(MsgField.cert_num.getFieldCode(), "4123432435234");
		map.put(MsgField.cert_type.getFieldCode(), "s");
		map.put(MsgField.cust_name.getFieldCode(), "123");
		map.put(MsgField.mobile_phone.getFieldCode(), "13222223333");
		map.put(MsgField.tel.getFieldCode(), "82349845");
		map.put(MsgField.zipcode.getFieldCode(), "518000");
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
		Map<String, MsgField> reqheadMap = MsgUtil.parseReqHead(msg);
		Map<String, MsgField> reqbodyMap = MsgUtil.parseReqBody(msg, MsgA0010.A0010REQ);
		// String resMsg = ConnectUtil.sendRecv(msg, 3, "GBK");
		Map<String, MsgField> headMap = MsgUtil.parseResHead(resMsg);
		Map<String, MsgField> bodyMap = MsgUtil.parseResBody(resMsg, MsgA0010.A0010RES);
	}

	private static void testA0011() throws Exception {
		Map<String, String> map = new HashMap<String, String>();

		map.put(MsgField.h_exch_code.getFieldCode(), "A0011");
		map.put(MsgField.account_no.getFieldCode(), "71221234123413241");
		map.put(MsgField.aip_no.getFieldCode(), "P089958050");
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
		Map<String, MsgField> reqheadMap = MsgUtil.parseReqHead(msg);
		Map<String, MsgField> reqbodyMap = MsgUtil.parseReqBody(msg, MsgA0011.A0011REQ);
		// String resMsg = ConnectUtil.sendRecv(msg, 3, "GBK");
		Map<String, MsgField> headMap = MsgUtil.parseResHead(resMsg);
		Map<String, MsgField> bodyMap = MsgUtil.parseResBody(resMsg, MsgA0011.A0011RES);
	}

	public static void testBuild() {
		Map<String, String> map = new HashMap<String, String>();
		map.put(MsgField.account_no.getFieldCode(), "71221234123413241");
		map.put(MsgField.area_code.getFieldCode(), "110000");
		map.put(MsgField.cert_num.getFieldCode(), "4123432435234");
		map.put(MsgField.cert_type.getFieldCode(), "s");
		map.put(MsgField.cust_name.getFieldCode(), "123");
		map.put(MsgField.mobile_phone.getFieldCode(), "13222223333");
		map.put(MsgField.tel.getFieldCode(), "82349845");
		map.put(MsgField.zipcode.getFieldCode(), "518000");
		String msg = MsgUtil.buildReqMsg(map, MsgA0010.A0010REQ);
		System.out.println(msg);
	}

	public static void testParse() {
		String msg = "GGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG                                        A2040DT0000 处理成功                                                                                                                                  A2010   000000000136001                              2              1       100     1       2       201  1000克标准金锭                          000000000010000000000000000010000000207  100克标准金条                           00000000000100000000000000000100000000";
		Map<String, MsgField> headMap = MsgUtil.parseResHead(msg);
		System.out.println("开始打印响应报文头");
		printMap(headMap);
		System.out.println("开始打印响应报文体");
		List<Map<String, MsgField>> bodyList = MsgUtil.parseResBodyList(msg, MsgA2040.A2040RES);
		for (int i = 0; i < bodyList.size(); i++) {
			printMap(bodyList.get(i));
		}
	}

	private static void printMap(Map<String, MsgField> map) {
		for (Map.Entry<String, MsgField> entry : map.entrySet()) {
			System.out.println(entry.getKey() + "=" + entry.getValue().getValue());
		}
	}
}
