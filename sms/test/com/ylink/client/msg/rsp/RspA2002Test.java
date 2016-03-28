package com.ylink.client.msg.rsp;

import org.junit.Test;

import com.ylink.client.msg.RspMsg;

public class RspA2002Test {
	@Test
	public void testToStringRspMsg() {
		//��ʽ����Ӧ����,����ͷ+������
		RspMsg  rspMsg = new RspMsg();
		String s = rspMsg.toString();
		System.out.println("��Ӧ����,����ͷ+������:" + s.length());
		System.out.println("��Ӧ����,����ͷ+������:" + s);
	}
	
	@Test
	public void testParseRspMsg() {
		//������Ӧ����			(������δʵ��)
		
		//ģ��һ����Ӧ����
		RspMsg m_rspMsg = new RspMsg();
		m_rspMsg.setH_auth_lvl("test");
		m_rspMsg.setH_bank_no("2222");
		String str = m_rspMsg.toString();
		System.out.println("ģ�����Ӧ���ĳ���:" + str.length());
		System.out.println("ģ�����Ӧ����:" + str);
		
		//������Ӧ����
		RspMsg rspMsg = new RspMsg();
		rspMsg.parse(m_rspMsg.toString());
		System.out.println("����������һ���ֶν��:" + rspMsg.getH_bank_no());
	}
	
	@Test
	public void testRsp() {
		/**
		 * ���ø����toStringBuffer��������Ӧ������������Ӧ����,����Ӧ�����Ƿ���װ��ȷ�Ĳ���
		 */
		RspA2002 rspA2002 = new RspA2002();
		StringBuffer sb = rspA2002.toStringBuffer();
		System.out.println("��Ӧ���ĳ���:" + sb.length());
		System.out.println("��Ӧ����:" + sb.toString());
	}
}
