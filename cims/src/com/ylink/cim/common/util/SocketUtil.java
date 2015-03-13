package com.ylink.cim.common.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import com.ylink.cim.common.msg.SocketParams;
import com.ylink.cim.common.msg.handle.MsgUtil;
import com.ylink.cim.common.msg.util.socket.ConnectionAdapter;
import com.ylink.cim.common.msg.util.socket.ConnectionProvider;

import flink.etc.BizException;
import flink.util.SpringContext;

public class SocketUtil {
	private static Logger logger = Logger.getLogger(SocketUtil.class);
	

	public static String recvMsg(Socket socket, int timeoutSeconds, String charset,
			boolean closeSocket) throws BizException {
		try {
			
			socket.setSoTimeout(timeoutSeconds * 1000);
			InputStream is = socket.getInputStream();
			DataInputStream dis = new DataInputStream(is);
			byte[] b=new byte[8];
			dis.read(b);
			int len=Integer.parseInt(new String(b));
			// 获取报文头字节数
			byte[] msgBuf = new byte[len];
			int n = 0;
			while (n < len) {
				byte[] temp = new byte[1024];
				int onceLen = dis.read(temp);
				if (onceLen > 0) {
					for (int i = 0; i < onceLen; i++) {
						msgBuf[n+i] = temp[i];
					}
					n+=onceLen;
				}
			}
			/*
			if (n != len) {
				throw new BizException(String.format("报文长度字节数不足%d<%d", n, len));
			}*/
			String msg = new String(msgBuf, charset);
//			logger.debug("收到报文[" + msg + "]");
			if (closeSocket) {
				socket.close();
			}
			return msg;
		} catch (Exception e) {
			logger.error("接收报文出错:" + e.getMessage());
			e.printStackTrace();
			if (closeSocket) {
				try {
					socket.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			throw new BizException("Error:接收报文出错," + e.getMessage());
		}
	}

	public static void sendMsg(Socket socket, String sendMsg, int timeoutSeconds, String charset) throws BizException {
		try {
			OutputStream os = socket.getOutputStream();
			DataOutputStream dataOutputStream=new DataOutputStream(os);
			int len=sendMsg.getBytes().length;//渠道接口文档要求前4位传报文长度，不足的补0
			String lenStr=MsgUtil.fill(len+"", '0', 8, 'L');
			dataOutputStream.write(lenStr.getBytes());
			dataOutputStream.write(sendMsg.getBytes(charset));
			dataOutputStream.flush();
//			logger.debug("发送报文:[" + sendMsg + "]");
			// socket.shutdownOutput();
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

	public static Socket sendMsg(String ipAddr, int port, String sendMsg, int timeoutSeconds, String charset)
			throws BizException {
		Socket socket = null;
		try {
			socket = new Socket();
			SocketAddress endpoint = new InetSocketAddress(ipAddr, port);
			socket.connect(endpoint, timeoutSeconds * 1000);
			SocketUtil.sendMsg(socket, sendMsg, timeoutSeconds, charset);
			return socket;
		} catch (Exception e) {
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			throw new BizException("与定投系统通信失败");
		}
	}

	public static String sendRecv(String ipAddr, int port, String sendMsg, int timeoutSeconds, String charset,
			int headLen, int footLen, boolean closeSocket) throws BizException {
		Socket socket = SocketUtil.sendMsg(ipAddr, port, sendMsg, timeoutSeconds, charset);
		return SocketUtil.recvMsg(socket, timeoutSeconds, charset,  closeSocket);
	}
	@Deprecated()
	public static String sendRecInPool(String sendMsg) throws BizException {
		SocketParams socketParams = (SocketParams)SpringContext.getService("socketParams");
		ConnectionAdapter socket = ConnectionProvider.getInstance().getConnection();
		SocketUtil.sendMsg(socket, sendMsg, socketParams.getTimeout(), socketParams.getCharset());
		logger.debug("请求报文["+sendMsg+"]");
		String resMsg = SocketUtil.recvMsg(socket, socketParams.getTimeout(), socketParams.getCharset(),  false);
		logger.debug("响应报文["+resMsg+"]");
		socket.release();
		return resMsg;
	}
	
	public static String sendRec(String sendMsg) throws BizException {
		SocketParams socketParams = (SocketParams)SpringContext.getService("socketParams");
		Socket socket=SocketUtil.sendMsg(socketParams.getHostName(), socketParams.getPortNum(), sendMsg, socketParams.getTimeout(), socketParams.getCharset());
		logger.debug("请求报文["+sendMsg+"]");
		String resMsg = SocketUtil.recvMsg(socket, socketParams.getTimeout(), socketParams.getCharset(),  true);
		logger.debug("响应报文["+resMsg+"]");
		return resMsg;
	}

}