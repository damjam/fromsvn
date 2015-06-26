package com.ylink.cim.common.msg.test;

import java.util.Map;

import com.ylink.cim.common.msg.handle.HeadReqUtils;
import com.ylink.cim.common.msg.handle.MsgA0013;
import com.ylink.cim.common.msg.handle.MsgField;
import com.ylink.cim.common.msg.handle.MsgUtil;
import com.ylink.cim.common.util.SocketUtil;

public class TestMsgA0013 {

	public static void main(String[] args) throws Exception{
		//testBuild();
		//test1();
		//testParse();
//		testA0010();
		testA0013();
	}
	private static void testA0013() throws Exception{
		Map map=HeadReqUtils.encapsulationHeadReqParam();
		
		map.put(MsgField.h_exch_code.getFieldCode(), "A0013");
		
		map.put(MsgField.aip_no.getFieldCode(), "P089958069");
		map.put(MsgField.account_no.getFieldCode(), "2312312");
		String msg = MsgUtil.buildReqMsg(map, MsgA0013.A0013REQ);
		/*int len = msg.getBytes().length;
		StringBuilder prefix = MsgUtil.fill(new StringBuilder(msg), '0', 4-String.valueOf(len).length(), 'L');
		prefix.append(msg);*/
//		msg = "                                        GGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGA0010                                      20130523     0000136001                                  2              1       100                                                                                     123            123                            s 123                                                                   110000    13222223333                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    ";
		String ip = "168.33.120.25";
		int port = 13000;
		/*Socket socket = SocketUtil.sendMsg(ip, port, msg, 5, "GBK");
		String  resMsg = SocketUtil.recvMsg(socket, 5, "GBK", true);*/
		//Socket socket2 = ConnectionProvider.getInstance().getConnection();
		//SocketUtil.sendMsg(socket2, msg, 3, "GBK");
		String resMsg = SocketUtil.sendRecInPool(ip);
		//String resMsg = ConnectUtil.sendRecv(msg, 3, "GBK");
		Map<String, MsgField> headMap = MsgUtil.parseResHead(resMsg);
		Map<String, MsgField> bodyMap = MsgUtil.parseResBody(resMsg, MsgA0013.A0013RES);
	}
	
	
	
}
