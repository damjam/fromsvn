package com.ylink.cim.appserver.task;

import org.apache.log4j.Logger;

//��ʱ������������̳����������
public abstract class BaseCmdTask extends Thread {
	private Logger logger = Logger.getLogger(BaseCmdTask.class);
	private String cmdName;

	// ����id ������߳�Ҫ��������id���޸�������״̬Ϊ�ɹ�����ʧ��
	private String cmdId;

	public BaseCmdTask() {

	}

	public BaseCmdTask(String strCmdName) {
		cmdName = strCmdName;
	}

	protected void doRun() {
		logger.debug("��ʼ��������[" + cmdName + "]");
	}

	public String getCmdId() {
		return cmdId;
	}

	
	public void run() {
		logger.debug("BaseCmdTask.run...");
		doRun();
	}

	public void setCmdId(String cmdId) {
		this.cmdId = cmdId;
	}

	public void setCmdName(String strCmdName) {
		cmdName = strCmdName;
	}
}
