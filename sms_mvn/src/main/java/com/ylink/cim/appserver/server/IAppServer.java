package com.ylink.cim.appserver.server;

public interface IAppServer {
	// 具体服务线程实现该接口，并且在实现中要循环。
	public void doProcess() throws Exception;
}
