package com.ylink.cim.common.msg.util.socket;

import com.ylink.cim.common.msg.Constant;

public class Test {

	public static void main(String[] args) {
		test1();
	}

	/**
	 * ��������շֿ�,���ڵ���
	 */
	public static void test1() {
		ConnectionProvider provider = ConnectionProvider.getInstance();
		ConnectionAdapter socket = provider.getConnection();
		if (socket.isClosed()) {
			System.out.println("closed");
		}
		try {
			ConnectUtil.sendMsg(socket, "���ͱ���", Constant.CFG_SOCKET_TIMEOUT_TIME, "GBK");
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
	 * ���������������ȶ�ʱ���ô˷���
	 */
	public static void test2() {
		try {
			String msg = ConnectUtil.sendRecv("���ͱ���", Constant.CFG_SOCKET_TIMEOUT_TIME, "GBK");
			System.out.println("��Ӧ����Ϊ" + msg);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
