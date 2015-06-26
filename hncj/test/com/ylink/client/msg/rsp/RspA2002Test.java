package com.ylink.client.msg.rsp;

import org.junit.Test;

import com.ylink.client.msg.RspMsg;

public class RspA2002Test {
	@Test
	public void testToStringRspMsg() {
		//格式化响应报文,报文头+报文体
		RspMsg  rspMsg = new RspMsg();
		String s = rspMsg.toString();
		System.out.println("响应报文,报文头+报文体:" + s.length());
		System.out.println("响应报文,报文头+报文体:" + s);
	}
	
	@Test
	public void testParseRspMsg() {
		//解析响应报文			(解析还未实现)
		
		//模拟一个响应报文
		RspMsg m_rspMsg = new RspMsg();
		m_rspMsg.setH_auth_lvl("test");
		m_rspMsg.setH_bank_no("2222");
		String str = m_rspMsg.toString();
		System.out.println("模拟的响应报文长度:" + str.length());
		System.out.println("模拟的响应报文:" + str);
		
		//解析响应报文
		RspMsg rspMsg = new RspMsg();
		rspMsg.parse(m_rspMsg.toString());
		System.out.println("解析后其中一个字段结果:" + rspMsg.getH_bank_no());
	}
	
	@Test
	public void testRsp() {
		/**
		 * 调用父类的toStringBuffer方法把响应报文体生成响应报文,做响应报文是否组装正确的测试
		 */
		RspA2002 rspA2002 = new RspA2002();
		StringBuffer sb = rspA2002.toStringBuffer();
		System.out.println("响应报文长度:" + sb.length());
		System.out.println("响应报文:" + sb.toString());
	}
}
