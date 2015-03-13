package com.ylink.client.msg.req;

import org.junit.Test;

import com.ylink.cim.common.type.AipMode;
import com.ylink.client.msg.ReqMsg;
import com.ylink.client.msg.RspMsg;
import com.ylink.client.msg.rsp.RspA2002;
import com.ylink.client.view.AipPlanForm;

public class ReqA2002Test {
	@Test
	public void testToStringReqMsg() {
		//组装请求报文,报文头+报文体
		ReqMsg reqMsg = new ReqMsg();
		reqMsg.setH_auth_lvl("t");
		reqMsg.setH_bank_no("222");
		
		String s = reqMsg.toString();
		System.out.println("请求报文,报文头+报文体:" + s.length());
		System.out.println("请求报文,报文头+报文体:" + s);
	}
	
	@Test
	public void testParseRspMsg() {
		//解析请求报文		(测试通过)
		//请求报文以及响应报文的共有的方法可以写一个公共类,让父类去实现解析过程,子类只要调用就可以
		//报文体有很多种,可以写成按类型产生具体报文的工厂方法
		
		//模拟一个请求报文
		ReqMsg m_reqMsg = new ReqMsg();
		m_reqMsg.setH_auth_lvl("ta");
		m_reqMsg.setH_bank_no("234");
		String str = m_reqMsg.toString();
		System.out.println("模拟的请求报文长度:" + str.length());
		System.out.println("模拟的请求报文:" + str);
		
		//解析请求报文
		ReqMsg reqMsg = new ReqMsg();
		reqMsg.parse(m_reqMsg.toString());
		System.out.println("解析后其中一个字段结果:" + reqMsg.getH_auth_lvl());
		System.out.println("解析后其中一个字段结果:" + reqMsg.getH_bank_no());
	}
	
	@Test
	public void testReq() {
		/**
		 * 查询表单
		 */
		AipPlanForm planForm = new AipPlanForm();
		planForm.setAccount_no("account_no_test");
		planForm.setAcct_no("acct_no_test");
		planForm.setAip_mode(AipMode.MODE_1);
		
		/**
		 * 预计在服务层调用客户定投计划查询
		 * 做查询表单与报文字段的转换
		 * 进而在服务层里做报文的组装,预想设计策略模式以及工厂方法组装相应报文体
		 */
		ReqA2002 a2002 = new ReqA2002();
		a2002.account_no = planForm.getAccount_no();
		a2002.aip_type = planForm.getAip_mode().getValue();
		
		String s = a2002.toString();	//调用父类的toString方式格式化请求报文体
		System.out.println("请求报文体长度:" + s.length());
		System.out.println("请求报文体:" + s);
		
		ReqA2002 new_a2002 = new ReqA2002();
		try {
			new_a2002.Parse(s);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
}
