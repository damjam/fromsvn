package com.ylink.cim.common.msg.util.socket;

import com.ylink.cim.common.msg.Constant;


public class Test {

	public static void main(String[] args) {
		test1();
	}
	/**
	 * 发送与接收分开,便于调试
	 */
	public static void test1(){
		ConnectionProvider provider = ConnectionProvider.getInstance();
		ConnectionAdapter socket = provider.getConnection();
		if (socket.isClosed()) {
			System.out.println("closed");
		}
		try {
			ConnectUtil.sendMsg(socket, "发送报文", Constant.CFG_SOCKET_TIMEOUT_TIME, "GBK");
		} catch (Exception e) {
			e.getMessage();
		}
		try {
			String msg = ConnectUtil.recvMsg(socket, Constant.CFG_SOCKET_TIMEOUT_TIME, "GBK");
			System.out.println(msg);
		} catch (Exception e) {
			e.getMessage();
		}
		socket.release();
	}
	/**
	 * 单步操作，运行稳定时采用此方法
	 */
	public static void test2(){
		try {
			String msg = ConnectUtil.sendRecv("发送报文", Constant.CFG_SOCKET_TIMEOUT_TIME, "GBK");
			System.out.println("响应报文为"+msg);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
}
