package com.ylink.cim.common.msg.util.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

import org.apache.log4j.Logger;

import flink.etc.BizException;

public class ConnectUtil {
	private static Logger logger = Logger.getLogger(ConnectUtil.class);

	public static String recvMsg(ConnectionAdapter socket, int timeoutSeconds, String charset) throws BizException {
		try {
			socket.setSoTimeout(3000);
			InputStream inputStream = socket.getInputStream();

			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "GBK"));

			char[] contents = new char[1024];

			StringBuffer sb = new StringBuffer();
			int readLength = 0;
			while ((readLength = reader.read(contents)) != -1) {
				sb.append(new String(contents, 0, readLength));

				// sb.toString().endsWith(endFlag);
				// break;
			}

			return sb.toString();
		} catch (Exception e) {
			logger.error("接收报文出错:" + e.getMessage());
			e.printStackTrace();
			throw new BizException("接收报文出错:" + e.getMessage());
		} finally {
			// socket.close();
		}
	}

	public static void sendMsg(Socket socket, String sendMsg, int timeoutSeconds, String charset) throws BizException {
		try {
			OutputStream os = socket.getOutputStream();
			os.write(sendMsg.getBytes(charset));
			os.flush();

		} catch (Exception e) {
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			throw new BizException("Socket通讯异常:" + e.getMessage());
		}
	}

	public static String sendRecv(String sendMsg, int timeoutSeconds, String charset) throws BizException {
		ConnectionProvider provider = ConnectionProvider.getInstance();
		ConnectionAdapter socket = provider.getConnection();
		sendMsg(socket, sendMsg, timeoutSeconds, charset);
		return recvMsg(socket, timeoutSeconds, charset);
	}

}
