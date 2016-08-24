package com.ylink.cim.appserver.task;

import org.apache.log4j.Logger;

//定时或者其他命令继承自这个基类
public abstract class BaseCmdTask extends Thread {
	private Logger logger = Logger.getLogger(BaseCmdTask.class);
	private String cmdName;

	// 命令id 具体的线程要根据命令id，修改命令表的状态为成功或者失败
	private String cmdId;

	public BaseCmdTask() {

	}

	public BaseCmdTask(String strCmdName) {
		cmdName = strCmdName;
	}

	protected void doRun() {
		logger.debug("开始启动命令[" + cmdName + "]");
	}

	public String getCmdId() {
		return cmdId;
	}

	@Override
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
