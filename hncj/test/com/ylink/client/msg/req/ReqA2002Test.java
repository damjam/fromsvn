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
		//��װ������,����ͷ+������
		ReqMsg reqMsg = new ReqMsg();
		reqMsg.setH_auth_lvl("t");
		reqMsg.setH_bank_no("222");
		
		String s = reqMsg.toString();
		System.out.println("������,����ͷ+������:" + s.length());
		System.out.println("������,����ͷ+������:" + s);
	}
	
	@Test
	public void testParseRspMsg() {
		//����������		(����ͨ��)
		//�������Լ���Ӧ���ĵĹ��еķ�������дһ��������,�ø���ȥʵ�ֽ�������,����ֻҪ���þͿ���
		//�������кܶ���,����д�ɰ����Ͳ������屨�ĵĹ�������
		
		//ģ��һ��������
		ReqMsg m_reqMsg = new ReqMsg();
		m_reqMsg.setH_auth_lvl("ta");
		m_reqMsg.setH_bank_no("234");
		String str = m_reqMsg.toString();
		System.out.println("ģ��������ĳ���:" + str.length());
		System.out.println("ģ���������:" + str);
		
		//����������
		ReqMsg reqMsg = new ReqMsg();
		reqMsg.parse(m_reqMsg.toString());
		System.out.println("����������һ���ֶν��:" + reqMsg.getH_auth_lvl());
		System.out.println("����������һ���ֶν��:" + reqMsg.getH_bank_no());
	}
	
	@Test
	public void testReq() {
		/**
		 * ��ѯ��
		 */
		AipPlanForm planForm = new AipPlanForm();
		planForm.setAccount_no("account_no_test");
		planForm.setAcct_no("acct_no_test");
		planForm.setAip_mode(AipMode.MODE_1);
		
		/**
		 * Ԥ���ڷ������ÿͻ���Ͷ�ƻ���ѯ
		 * ����ѯ���뱨���ֶε�ת��
		 * �����ڷ�����������ĵ���װ,Ԥ����Ʋ���ģʽ�Լ�����������װ��Ӧ������
		 */
		ReqA2002 a2002 = new ReqA2002();
		a2002.account_no = planForm.getAccount_no();
		a2002.aip_type = planForm.getAip_mode().getValue();
		
		String s = a2002.toString();	//���ø����toString��ʽ��ʽ����������
		System.out.println("�������峤��:" + s.length());
		System.out.println("��������:" + s);
		
		ReqA2002 new_a2002 = new ReqA2002();
		try {
			new_a2002.Parse(s);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
}
