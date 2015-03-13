package com.ylink.cim.appserver.task;

import org.apache.log4j.Logger;

//��ʱ������������̳����������
public class BaseCmdTask extends Thread {
	private Logger logger = Logger.getLogger(BaseCmdTask.class);
	private String cmdName;

	// ����id ������߳�Ҫ��������id���޸�������״̬Ϊ�ɹ�����ʧ��
	private Long cmdId;

	public BaseCmdTask() {

	}

	public BaseCmdTask(String strCmdName) {
		cmdName = strCmdName;
	}

	protected void doRun() {
		logger.debug("��ʼ��������[" + cmdName + "]");
	}

	public Long getCmdId() {
		return cmdId;
	}

	
	public void run() {
		logger.debug("BaseCmdTask.run...");
		doRun();
	}

	public void setCmdId(Long cmdId) {
		this.cmdId = cmdId;
	}

	public void setCmdName(String strCmdName) {
		cmdName = strCmdName;
	}
}
