package com.ylink.cim.common.msg.util.socket;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ConnectionAdapter extends Socket {
	/**
	 * ����״̬
	 */
	private boolean status = true;
	/**
	 * ���ӳ����������
	 */
	private boolean extra = false;
	/**
	 * Ĭ�ϵĹ��캯��
	 */
	public ConnectionAdapter() {
		super();
	}

	public ConnectionAdapter(String host, int port) throws UnknownHostException, IOException {
		super(host, port);
	}
	public ConnectionAdapter(String host, int port, boolean extra) throws UnknownHostException, IOException {
		super(host, port);
		extra = true;
	}
	/**
	 * �жϴ������Ƿ����
	 * 
	 * @return boolean ���з���ture,����false
	 */
	public boolean isFree() {
		return status;
	}
	public boolean isExtra() {
		return extra;
	}
	/**
	 * ��ʹ�ô����ӵ�ʱ������״̬Ϊfalse��æµ��
	 */
	public void setBusy() {
		this.status = false;
	}

	/**
	 * ���ͻ��˹ر����ӵ�ʱ��״̬����Ϊtrue(���У�
	 */
	public void release() {
		status = true;
		//�������ɵģ�����ر�
		if (extra) {
			try {
				super.close();
			} catch (Exception e) {
				
			}
		}
	}

	public void destroy() {
		release();
		try {
			super.close();
		} catch (Exception e) {
			
		}
	}
	

}