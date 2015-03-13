package com.ylink.cim.common.msg;

import java.net.Socket;

import org.apache.log4j.Logger;

import com.ylink.cim.common.util.SocketUtil;

import flink.etc.BizException;
import flink.util.SpringContext;


public class GoldSorket {
	static Logger log = Logger.getLogger(GoldSorket.class); // ��־��¼��Ϣ
	public static  String sendMessage(String sendMsg) throws BizException {
		String rspMsg = "";
		try {
			SocketParams socketParams = (SocketParams)SpringContext.getService("socketParams");
			Socket socket = SocketUtil.sendMsg(socketParams.getHostName(),
					socketParams.getPortNum(), sendMsg,
					socketParams.getTimeout(), socketParams.getCharset());
			rspMsg = SocketUtil.recvMsg(socket, socketParams.getTimeout(),
					socketParams.getCharset(), true);
		} catch (Exception e) {
			rspMsg = "";
			log.error("error:����/���ձ����쳣" + e.getMessage());
			throw new BizException(e.getMessage());
		}
		return rspMsg;
	}

}